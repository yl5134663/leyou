package com.interceptor;

import com.config.JwtProperties;
import com.pojo.UserInfo;
import com.utils.CookieUtils;
import com.utils.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 这里我们使用了ThreadLocal来存储查询到的用户信息，线程内共享，因此请求到达Controller后可以共享User
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private JwtProperties jwtProperties;
    //定义线程域，存放登录用户
    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();
    public LoginInterceptor(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, "LY_TOKEN");
        if (StringUtils.isBlank(token)) {
            //未登录,返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        try {
            //解析成功,说明已经登录
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            //放入线程域
            tl.set(userInfo);
            return true;
        } catch (Exception e) {
            // 抛出异常，证明未登录,返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        tl.remove();
    }
    public static UserInfo getLoginUser() {
        return tl.get();
    }
}
