package cn.nanchengyu.reggie.config;

import cn.nanchengyu.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

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

    /**
     * long字符串转换为字符串，防止前端时间戳转换为时间失败或者id精度丢失
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
         log.info("开始扩展消息转换器");
        //1. 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //2. 设置对象转换器，底层使用Jackson将Java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //3. 将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0,messageConverter);


    }

}
