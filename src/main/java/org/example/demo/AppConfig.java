package org.example.demo;


import org.example.demo.config.WebAppConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//标记当前类是一个配置类，用来替代传统的 XML 配置文件
@Configuration
//告诉 Spring 扫描 org.example.demo 包及其子包下所有带有 @Component（及其衍生注解）的类，并将它们注册为 Bean。
// 如果不写 basePackages，默认扫描当前配置类所在包及其子包。
@ComponentScan(
        basePackages = "org.example.demo",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = WebAppConfig.class)
)
public class AppConfig {
}