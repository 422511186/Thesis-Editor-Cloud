package com.cmgzs.service.impl;

import com.cmgzs.domain.Content;
import com.cmgzs.domain.UserContext;
import com.cmgzs.exception.CustomException;
import com.cmgzs.mapper.ContentMapper;
import com.cmgzs.service.ContentService;
import com.cmgzs.service.RedisService;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.cmgzs.constant.RedisConstant.archive_lock;

/**
 * @author huangzhenyu
 * @date 2022/9/24
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RedisService redisService;

    private static final String EXPIRE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then   redis.call('expire', KEYS[1], ARGV[2]); return 1; else return 0 end";

    /**
     * 打开文档
     *
     * @param archiveId 文档id
     * @return 结果
     */
    @Override
    public List<Content> openArchive(String archiveId) {

        int open = keepOpen(archiveId);
        if (open == 0) {
            throw new CustomException("该文档已被占用");
        }

        Content content = new Content();
        content.setArchiveId(archiveId);
        return contentMapper.findAll(Example.of(content));
    }

    /**
     * 保持打开状态（需要续签）
     *
     * @param archiveId 打开的文档id
     * @return 结果
     */
    @Override
    public int keepOpen(String archiveId) {

        String userId = UserContext.getUserId();
        String redis_key = archive_lock + archiveId;

        /** 利用redis单线程模型，setnx的原子操作，尝试进行加锁操作 */
        Boolean setIfAbsent = redisService.redisTemplate.opsForValue().setIfAbsent(redis_key, UserContext.getUserId(), 5, TimeUnit.MINUTES);
        //无锁，则直接上锁
        if (Boolean.TRUE.equals(setIfAbsent)) {
            return 1;
        }
        /** 有锁，则需要判断当前持有锁对象是否为当前用户 */
        // 指定 lua 脚本，并且指定返回值类型
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(EXPIRE_LOCK_LUA_SCRIPT, Long.class);
        /**
         * 参数一:redisScript
         * 参数二:key列表
         * 参数三:arg（可多个）
         */
        Long result = (Long) redisService.redisTemplate.execute(redisScript, Collections.singletonList(redis_key), userId, 30 * 2 * 5);
        if (result == null) {
            throw new CustomException("异常错误");
        }
        return result.intValue();

//        String cacheUserId = redisService.getCacheObject(redis_key);
//
//        /**非当前用户占用文档*/
//        if (cacheUserId != null && !cacheUserId.equals(userId)) {
//            return 0;
//        }
//        //续签
//        redisService.setCacheObject(redis_key, userId, 5, TimeUnit.MINUTES);
//        return 1;
    }

    /**
     * 修改和删除的统一入口
     *
     * @param contents 待操作的content
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateContents(List<Content> contents) {

        return 0;
    }

    /**
     * 分块修改 （单个content或者可以按页码）
     * 修改文档内容  -- 修改包括update和insert
     *
     * @param contents 需要修改的content
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveContents(List<Content> contents) {
        contentMapper.saveAll(contents);
        return 1;
    }

    /**
     * @param contentIds 需要删除的content ids
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteContents(List<String> contentIds) {
        contentIds.forEach(contentMapper::deleteById);
        return 1;
    }
}
