package com.cmgzs.service.impl;

import com.cmgzs.mapper.PermissionsMapper;
import com.cmgzs.mapper.UserMapper;
import com.cmgzs.service.PermissionsService;
import com.cmgzs.utils.text.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PermissionsServiceImpl implements PermissionsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private PermissionsMapper permissionsMapper;

    /**
     * 获取当前账号对应的权限列表
     *
     * @param id 用户id
     * @return 结果
     */
    @Override
    public List<GrantedAuthority> getPermissions(String id) {
        List<String> authorityList = permissionsMapper.getPermissions(id);
        if (authorityList == null || authorityList.stream().filter(StringUtils::isNotEmpty).count() == 0)
            return new ArrayList<>();
        return authorityList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
