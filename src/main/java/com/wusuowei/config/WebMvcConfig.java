package com.wusuowei.config;


import com.wusuowei.interceptor.AccessLimitInterceptor;
import com.wusuowei.interceptor.PaginationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private PaginationInterceptor paginationInterceptor;

    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:*","http://111.231.13.130:*")  // 允许所有本地地址和端口的请求
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)
                .allowedHeaders("*");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(paginationInterceptor);
        registry.addInterceptor(accessLimitInterceptor);
    }

}
