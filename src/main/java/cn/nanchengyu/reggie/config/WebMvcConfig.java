package cn.nanchengyu.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * ClassName: WebMvcConfig
 * Package: cn.nanchengyu.reggie.config
 * Description:
 *
 * @Author 南城余
 * @Create 2024/1/15 12:49
 * @Version 1.0
 */
@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {
    // 设置静态资源映射 允许访问前端静态所有资源
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
      log.info("开始静态资源映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
}
