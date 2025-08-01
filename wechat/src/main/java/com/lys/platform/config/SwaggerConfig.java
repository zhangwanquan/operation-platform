package com.lys.platform.config;

import com.google.common.base.Predicates;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jiangzhili
 * @desc Swagger配置
 * @date 2015/7/28
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger.enable", value = "enable", havingValue = "true")
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    /**
     * api配置
     *
     * @return 配置
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select() // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.lys.platform.controller"))// 对所有api进行监控
                .paths(PathSelectors.any())// 对所有api进行监控
                //不显示错误的接口地址
//                .paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
//                .paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
                .build();
    }


    /**
     * 配置描述
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("商场小程序端口接口文档")
                .description("简单优雅的restful风格")
                .version("1.0.0")
                .build();
    }
}
