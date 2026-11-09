package org.example.demo.user.model;

/**
 * 用户实体（POJO）。
 * 只做数据承载，不注册为 Bean —— 实体对象由业务代码 new 出来，交给容器管理没有意义。
 */
public class User {

    private Long id;
    private String username;
    private String email;
    private int age;

    public User() {
    }

    public User(String username, String email, int age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s', email='%s', age=%d}",
                id, username, email, age);
    }
}

