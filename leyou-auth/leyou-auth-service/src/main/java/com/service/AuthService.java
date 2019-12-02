package com.service;

import com.client.UserClient;
import com.config.JwtProperties;
import com.pojo.User;
import com.pojo.UserInfo;
import com.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 查询用户,生成token
     * @param username
     * @param password
     * @return
     */
    public String authentication(String username, String password) {
        try {
            //调用微服务
            User user = userClient.queryUser(username, password);
            if (user == null) {
                return null;
            }
            //如果查询有结果,生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()), jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
