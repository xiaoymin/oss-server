/*
 * Copyright (C) 2017 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Properties;

/***
 * mybaitis-plus中间件配置文件
 * @since:province_cdc_III 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 10:53
 */
@Configuration
@MapperScan(basePackages = {"com.xiaominfo.oss.module.dao"})
public class MybatisPlusConfiguration {

    Log log= LogFactory.get();
    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(PlatformTransactionManager platformTransactionManager){
        AspectJExpressionPointcut aspectJExpressionPointcut=new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression("execution (* com.xiaominfo.oss.service..*.*(..))");
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(aspectJExpressionPointcut);

        TransactionInterceptor transactionInterceptor=new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(platformTransactionManager);
        Properties transactionProperties=new Properties();
        transactionProperties.setProperty("get*", "PROPAGATION_REQUIRED,-Throwable");
        transactionProperties.setProperty("query*", "PROPAGATION_REQUIRED,-Throwable");
        transactionProperties.setProperty("select*", "PROPAGATION_REQUIRED,-Throwable");
        transactionProperties.setProperty("insert*", "PROPAGATION_REQUIRED,-Throwable");
        transactionProperties.setProperty("save*", "PROPAGATION_REQUIRED,-Throwable");
        transactionProperties.setProperty("del*", "PROPAGATION_REQUIRED,-Throwable");
        transactionProperties.setProperty("add*", "PROPAGATION_REQUIRED,-Throwable");
        transactionProperties.setProperty("group*", "PROPAGATION_REQUIRED,-Throwable");
        transactionProperties.setProperty("upd*", "PROPAGATION_REQUIRED,-Throwable");
        transactionProperties.setProperty("update*", "PROPAGATION_REQUIRED,-Throwable");
        transactionProperties.setProperty("delete*", "PROPAGATION_REQUIRED,-Throwable");
        transactionProperties.setProperty("page*", "PROPAGATION_REQUIRED,-Throwable");
        transactionInterceptor.setTransactionAttributes(transactionProperties);

        advisor.setAdvice(transactionInterceptor);
        return advisor;
    }
}
