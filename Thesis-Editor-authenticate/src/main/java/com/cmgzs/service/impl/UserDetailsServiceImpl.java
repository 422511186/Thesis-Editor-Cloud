package com.cmgzs.service.impl;


import com.cmgzs.domain.MyUserDetails;
import com.cmgzs.domain.auth.User;
import com.cmgzs.mapper.UserMapper;
import com.cmgzs.service.PermissionsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private PermissionsService permissionsServiceImpl;

    private final GrantedAuthority DEFAULT_ROLE = new SimpleGrantedAuthority("NONE");

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(User user) {
        // 2. 设置角色
        List<GrantedAuthority> auths = permissionsServiceImpl.getPermissions(user.getId());
        if (auths == null || auths.size() == 0) {
            auths.add(DEFAULT_ROLE);
        }
        return new MyUserDetails(user, auths);
    }

}
