package org.example.demo.user.service;

import org.example.demo.user.dao.UserDao;
import org.example.demo.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务逻辑层。
 * <p>
 * {@code @Service} 同样是 {@code @Component} 的语义化特化注解，表示"业务服务层"。
 * <p>
 * 依赖注入方式：<b>构造器注入</b>。只有一个构造器时，Spring 4.3+ 会自动用它完成注入，
 * 无需写 {@code @Autowired}；而且字段可以声明为 {@code final}，保证不可变、便于单元测试。
 */
@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /** 注册新用户，带最基本的业务校验 */
    public User createUser(String username, String email, int age) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (age <= 0 || age > 150) {
            throw new IllegalArgumentException("年龄不合法: " + age);
        }
        User user = new User(username.trim(), email, age);
        return userDao.save(user);
    }

    /** 按 id 查询，查不到直接抛异常 */
    public User getUser(Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在, id=" + id));
    }

    /** 查询全部用户 */
    public List<User> listUsers() {
        return userDao.findAll();
    }

    /** 用户总数 */
    public int countUsers() {
        return userDao.count();
    }
}

