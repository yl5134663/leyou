package com.api;

import com.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {
    @GetMapping("query")
    public User queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );
}
