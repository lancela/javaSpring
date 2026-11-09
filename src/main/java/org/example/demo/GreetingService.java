package org.example.demo;


import org.springframework.stereotype.Component;
// 标记为Spring管理的Bean
@Component
public class GreetingService {
    private final UserDao userDao;
    // 构造器注入，Spring会自动匹配并注入UserDao实例
    public GreetingService(UserDao userDao) {
        this.userDao = userDao;
    }
    public void addUser() {
        userDao.insertUser();
        System.out.println("用户添加成功");
    }
}
