package org.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// 数据访问层Bean
//@Component
@Service
public class UserDao {
    public void insertUser() {
        System.out.println("执行数据库插入操作");
    }
}
