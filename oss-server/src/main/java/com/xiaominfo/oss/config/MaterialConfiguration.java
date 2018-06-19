/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.config;

import com.xiaominfo.oss.filter.GlobalRequestMappingFilter;
import com.xiaominfo.oss.filter.LoginInterceptor;
import com.xiaominfo.oss.service.MaterialConfig;
import com.xiaominfo.oss.service.impl.MaterialConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/05/30 11:06
 */
@Configuration
public class MaterialConfiguration implements WebMvcConfigurer {

    @Value(value = "${material.root}")
    private String root;
    @Value(value = "${material.invokingRoot}")
    private String invokingRoot;
    @Value(value = "${material.pathstyle}")
    private String pathstyle;


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new JsonpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor());
    }

    @Bean
    public MaterialConfig materialConfig(){
        MaterialConfigImpl materialConfig=new MaterialConfigImpl();
        materialConfig.setRootPath(root);
        materialConfig.setInvokingRoot(invokingRoot);
        materialConfig.setPathStyle(pathstyle);
        return materialConfig;
    }

    @Bean
    public FilterRegistrationBean  corsFilter(){
        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Bean
    public GlobalRequestMappingFilter globalRequestMappingFilter(){
        return new GlobalRequestMappingFilter();
    }


}
