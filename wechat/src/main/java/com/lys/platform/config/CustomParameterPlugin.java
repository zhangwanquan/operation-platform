package com.lys.platform.config;

import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/31 09:29
 * @version: 1.0
 */
@Component
public class CustomParameterPlugin implements ParameterBuilderPlugin {

    @Override
    public void apply(ParameterContext context) {
        // 自定义参数处理逻辑，避免空字符串转换异常
        context.parameterBuilder()
                .parameterAccess("access")
                .description("description");
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
