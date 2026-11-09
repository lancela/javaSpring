package org.example.demo.user.config;

import jakarta.annotation.PostConstruct;
import org.example.demo.user.model.User;
import org.example.demo.user.service.UserService;
import org.springframework.stereotype.Component;

/**
 * 演示用数据初始化器：容器启动后插入两条模拟数据。
 * <p>
 * 之所以单独建一个 Bean 而不写在 {@code UserDao} 里，是为了保持分层职责：
 * DAO 只负责存取，业务数据的准备交给"初始化器"通过 Service 完成。
 * <p>
 * 生命周期：{@code @PostConstruct} 在依赖注入完成后、Bean 可对外提供服务前调用，
 * 所以此时 {@code UserService} 已完全就绪。
 */
@Component
public class UserDataInitializer {

    private final UserService userService;

    public UserDataInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        System.out.println("[UserDataInitializer] 开始插入模拟数据 ...");
        User user1 = userService.createUser("张三", "zhangsan@example.com", 28);
        User user2 = userService.createUser("李四", "lisi@example.com", 32);
        System.out.println("[UserDataInitializer] 已插入 2 条数据：" + user1 + " / " + user2);
    }
}

