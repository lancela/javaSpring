package org.example.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        //AnnotationConfigApplicationContext 是 Spring 提供的基于 Java 配置的容器实现
        // 传入 AppConfig.class 后，容器会读取配置类上的 @ComponentScan，扫描并创建对应的 Bean。
        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        GreetingService greetingService = context.getBean(GreetingService.class);
        greetingService.addUser();

        if (context instanceof ConfigurableApplicationContext) {
            ((ConfigurableApplicationContext) context).close();
        }
    }
}