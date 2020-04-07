package com.tsingtec.mini.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lj
 * @Date 2020/4/7 10:31
 * @Version 1.0
 */
@Slf4j
@Configuration
public class EhCacheConfig {

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }
}
