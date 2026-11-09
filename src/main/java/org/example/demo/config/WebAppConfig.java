package org.example.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 层配置类。
 * <p>
 * {@code @EnableWebMvc} 会导入 Spring MVC 的核心基础设施：
 * <ul>
 *     <li>{@code RequestMappingHandlerMapping} / {@code RequestMappingHandlerAdapter}（把 HTTP 请求映射到 @RequestMapping 方法）；</li>
 *     <li>默认的 {@code HttpMessageConverter} 集合（classpath 上有 Jackson 时自动注册 JSON 转换器）；</li>
 *     <li>参数解析器（@RequestBody、@PathVariable 等）、异常解析器（@ExceptionHandler）。</li>
 * </ul>
 * 注意：{@code @EnableWebMvc} 会关闭 Spring Boot 式的自动配置，这里本来就只用原生 Spring，正好需要它。
 * <p>
 * {@code @ComponentScan} 与 AppConfig 相同，负责把 @RestController / @Service / @Repository 注册进本容器。
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.example.demo")
public class WebAppConfig implements WebMvcConfigurer {
}

