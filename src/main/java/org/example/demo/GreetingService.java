package org.example.demo;

import org.example.demo.user.model.User;
import org.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
// 标记为Spring管理的Bean
@Component
public class GreetingService {
    // （注入方式一）字段注入：@Autowired 直接作用在字段上
    @Autowired
    private UserService userService;
    // （注入方式二）构造器注入，Spring会自动匹配并注入依赖实例（推荐）
    //    public GreetingService(UserService userService) {
    //        this.userService = userService;
    //    }
    public void addUser() {
        User user = userService.createUser("greeting-user", "greeting@example.com", 20);
        System.out.println("用户添加成功 -> " + user);
    }
}
