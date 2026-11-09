package org.example.demo.user.dao;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.demo.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 数据访问层。
 * <p>
 * {@code @Repository} 是 {@code @Component} 的语义化特化注解：
 * <ul>
 *     <li>同样会被 {@code @ComponentScan} 扫描并注册为 Bean（Bean 名称默认 userDao）；</li>
 *     <li>额外语义是"数据访问层"，配合 Spring 的持久层异常转换机制（PersistenceExceptionTranslation）
 *     可把 JDBC/JPA 的检查型异常转换成 Spring 的 DataAccessException 体系。</li>
 * </ul>
 * 这里没有真实数据库，用一个内存 Map 模拟存储。
 */
@Repository
public class UserDao {

    /** 模拟数据库表 */
    private final Map<Long, User> table = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @PostConstruct
    public void init() {
        System.out.println("[UserDao] @PostConstruct  -> 模拟打开数据库连接 / 初始化连接池");
    }

    @PreDestroy
    public void destroy() {
        table.clear();
        System.out.println("[UserDao] @PreDestroy     -> 模拟关闭数据库连接 / 释放资源");
    }

    /** 新增用户，返回带主键的对象 */
    public User save(User user) {
        long id = idGenerator.incrementAndGet();
        user.setId(id);
        table.put(id, user);
        System.out.println("[UserDao] INSERT INTO t_user(id, username, email, age) VALUES ("
                + id + ", '" + user.getUsername() + "', '" + user.getEmail() + "', " + user.getAge() + ")");
        return user;
    }

    /** 按主键查询 */
    public Optional<User> findById(Long id) {
        System.out.println("[UserDao] SELECT * FROM t_user WHERE id = " + id);
        return Optional.ofNullable(table.get(id));
    }

    /** 查询全部（按 id 升序） */
    public List<User> findAll() {
        System.out.println("[UserDao] SELECT * FROM t_user");
        List<User> users = new ArrayList<>(table.values());
        users.sort(Comparator.comparing(User::getId));
        System.out.println("[UserDao] 查询结果：" + users);
        return users;
    }

    /** 统计总数 */
    public int count() {
        return table.size();
    }
}

