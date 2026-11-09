package org.example.demo;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.example.demo.config.WebAppConfig;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

/**
 * Web 版启动入口：以内嵌 Tomcat + Spring MVC 的方式跑起来，对外提供 REST 接口。
 * <p>
 * 启动后可用浏览器或 curl 访问：
 * <pre>
 *   POST http://localhost:8080/users  + JSON 请求体
 *   GET  http://localhost:8080/users
 *   GET  http://localhost:8080/users/1
 * </pre>
 * 说明：如果只想看 IoC / 生命周期的控制台演示，请运行 {@link Main}。
 */
public class WebMain {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        // 1. 创建 Tomcat 实例
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(PORT);
        tomcat.setBaseDir(new File("target/tomcat").getAbsolutePath());
        // 必须显式调用，否则不会创建默认的 HTTP Connector
        tomcat.getConnector();

        // 2. 创建 Spring Web 容器（读取 WebAppConfig：@EnableWebMvc + @ComponentScan）
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(WebAppConfig.class);
        springContext.setServletContext(null);   // 由 DispatcherServlet 在 init 时注入 ServletContext

        // 3. 注册 Spring MVC 的前端控制器 DispatcherServlet，并映射到 "/"
        Context tomcatContext = tomcat.addContext("", new File(".").getAbsolutePath());
        DispatcherServlet dispatcherServlet = new DispatcherServlet(springContext);
        Tomcat.addServlet(tomcatContext, "dispatcherServlet", dispatcherServlet);
        tomcatContext.addServletMappingDecoded("/", "dispatcherServlet");

        // 4. 优雅停机：Ctrl+C 时关闭 Spring 容器（触发 @PreDestroy）并停止 Tomcat
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[WebMain] 正在关闭 Spring 容器与 Tomcat ...");
            springContext.close();
            try {
                tomcat.stop();
                tomcat.destroy();
            } catch (Exception ignored) {
                // 停机阶段忽略异常
            }
        }, "shutdown-hook"));

        // 5. 启动
        tomcat.start();
        System.out.println("======================================================");
        System.out.println(" Spring MVC 已启动: http://localhost:" + PORT + "/users");
        System.out.println(" （按 Ctrl+C 停止）");
        System.out.println("======================================================");

        // 6. 阻塞主线程，等待请求
        tomcat.getServer().await();
    }
}