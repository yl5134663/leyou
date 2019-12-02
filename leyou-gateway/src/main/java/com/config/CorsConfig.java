package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
@Bean
    public CorsFilter corsFilter(){
    //添加Cors配置信息
    CorsConfiguration corsConfiguration=new CorsConfiguration();
    //1) 允许的域,不要写*，否则cookie就无法使用了
    corsConfiguration.addAllowedOrigin("http://manager.leyou.com");
    corsConfiguration.addAllowedOrigin("http://www.leyou.com");
    corsConfiguration.addAllowedOrigin("http://api.leyou.com");
    //是否发送cookie信息
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.addAllowedHeader("*");
    //2.添加映射路径，我们拦截一切请求
    UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
    configSource.registerCorsConfiguration("/**", corsConfiguration);
    return  new CorsFilter(configSource);

}
}
