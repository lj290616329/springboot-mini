package com.tsingtec.mini.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value("${swagger2.enable}")
    private boolean enable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiAdminInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tsingtec.mini.controller.manager"))
                .paths(PathSelectors.regex(".*/manager/.*")) // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build()
                .enable(enable);
    }


    private ApiInfo apiAdminInfo() {
        return new ApiInfoBuilder()
                .title("后台管理接口")//设置文档的标题
                .description("后台数据管理")//设置文档的描述->1.Overview
                .version("1.0")//设置文档的版本信息-> 1.1 Version information
                .build();
    }

    @Bean
    public Docket createMpRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("微信管理接口")
                .apiInfo(apiMpInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tsingtec.mini.controller.mp"))//api接口包扫描路径
                .paths(PathSelectors.regex(".*/mobile/.*"))//可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build();
    }
    private ApiInfo apiMpInfo() {
        return new ApiInfoBuilder()
                .title("微信管理接口")//设置文档的标题
                .description("微信开发接口实现的文档")//设置文档的描述->1.Overview
                .version("1.0")//设置文档的版本信息-> 1.1 Version information
                .build();
    }

    @Bean
    public Docket createWxsRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("小程序管理接口")
                .apiInfo(apiWxsInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tsingtec.mini.controller.mp"))//api接口包扫描路径
                .paths(PathSelectors.regex(".*/wxs/.*"))//可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build();
    }
    private ApiInfo apiWxsInfo() {
        return new ApiInfoBuilder()
                .title("小程序管理接口")//设置文档的标题
                .description("小程序管理接口开发接口实现的文档")//设置文档的描述->1.Overview
                .version("1.0")//设置文档的版本信息-> 1.1 Version information
                .build();
    }

}
