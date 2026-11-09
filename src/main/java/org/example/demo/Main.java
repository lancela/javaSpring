package org.example.demo;

import org.example.demo.user.controller.UserController;
import org.example.demo.user.model.User;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 控制台版启动入口：只用 IoC 容器演示 Bean 注册、依赖注入、生命周期回调。
 * <p>
 * 需要 Web（REST）接口请运行 {@link WebMain}。
 */
public class Main {
    public static void main(String[] args) {
        // 用 try-with-resources 管理容器：离开代码块时自动 close()，
        // 从而触发所有单例 Bean 的 @PreDestroy 回调。
        try (ConfigurableApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            // ---------- 原有示例：字段注入 ----------
            System.out.println("========== GreetingService 示例 ==========");
            GreetingService greetingService = context.getBean(GreetingService.class);
            greetingService.addUser();

            // ---------- user 模块：Controller -> Service -> Dao ----------
            System.out.println();
            System.out.println("========== User 模块示例（含启动时预置的 2 条模拟数据）==========");
            UserController userController = context.getBean(UserController.class);
            System.out.println("当前已有用户：");
            userController.list().forEach(u -> System.out.println("    " + u));

            System.out.println("新增一个用户：");
            System.out.println("    -> " + userController.create(new User("王五", "wangwu@example.com", 25)));

            System.out.println("查询 id=1 -> " + userController.get(1L));
            System.out.println("共 " + userController.list().size() + " 个用户：");
            userController.list().forEach(u -> System.out.println("    " + u));

            // 业务校验演示：非法年龄会被 UserService 拦下
            System.out.println();
            System.out.println("========== 参数校验示例 ==========");
            try {
                userController.create(new User("赵六", "zhaoliu@example.com", 100));
            } catch (IllegalArgumentException e) {
                System.out.println("注册失败：" + e.getMessage());
            }
        }
    }
}