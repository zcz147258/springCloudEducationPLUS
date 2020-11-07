package com.example.servicebase;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")//名称
                .apiInfo(webApiInfo())//接口信息
                .select()
//                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))//包含匹配
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//
                .build();
    }
    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                // 标题
                .title("古粒-课程中心Api文档")
                // 描述
                .description("描述了课程中心微服务定义接口")
                // 文档版本
                .version("1.0.0")
                .build();
    }
}
