package com.dl.auth_server.security;

import com.dl.auth_server.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: Damon
 * @Date: 2020/6/7
 */

@Service
@Primary
public class UserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //模拟一个用户，密码用new BCryptPasswordEncoder().encode("123")
        User user = new User();
        user.setUsername("admin");
        user.setPassword("$2a$10$hWfxScZlwCF3AI/qaVlB4uvbIVj0Qz31fVcEdvb.TOhzeY99SziJy");

        if (username.equals(user.getUsername())) {
            return user;
        }

        throw new UsernameNotFoundException("用户名不存在");
    }
}
