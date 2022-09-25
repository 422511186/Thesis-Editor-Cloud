package com.cmgzs.service.impl;

import com.cmgzs.domain.Content;
import com.cmgzs.domain.UserContext;
import com.cmgzs.mapper.ContentMapper;
import com.cmgzs.service.ContentService;
import com.cmgzs.service.RedisService;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    /**
     * 打开文档
     *
     * @param archiveId 文档id
     * @return 结果
     */
    @Override
    public List<Content> openArchive(String archiveId) {
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

        String cacheUserId = redisService.getCacheObject(archive_lock + archiveId);

        /*非当前用户占用文档*/
        if (cacheUserId != null && !cacheUserId.equals(userId)) {
            return 0;
        }
        //续签
        redisService.setCacheObject(archive_lock + archiveId, userId, 5, TimeUnit.MINUTES);
        return 1;
    }

    /**
     * 修改和删除的统一入口
     *
     * @param contents 待操作的content
     * @return 结果
     */
    @Override
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
    public int saveContents(List<Content> contents) {
        contentMapper.saveAll(contents);
        return 1;
    }

    /**
     * @param contentIds 需要删除的content ids
     * @return 结果
     */
    @Override
    public int deleteContents(List<String> contentIds) {
        contentIds.forEach(contentMapper::deleteById);
        return 1;
    }
}
