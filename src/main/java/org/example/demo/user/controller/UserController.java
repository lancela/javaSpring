package org.example.demo.user.controller;

import org.example.demo.user.model.User;
import org.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * 表现层（控制层）—— REST 接口。
 * <p>
 * {@code @RestController} = {@code @Controller} + {@code @ResponseBody}：
 * 方法返回值会经消息转换器（HttpMessageConverter）直接写回响应体，
 * 有 Jackson 时自动转成 JSON。
 * <p>
 * 依赖注入方式：{@code @Autowired} 构造器注入。
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST /users
     * 请求体：{"username":"张三","email":"zhangsan@example.com","age":28}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userService.createUser(user.getUsername(), user.getEmail(), user.getAge());
    }

    /** GET /users —— 查询全部用户 */
    @GetMapping
    public List<User> list() {
        return userService.listUsers();
    }

    /** GET /users/{id} —— 按 id 查询，查不到返回 404 */
    @GetMapping("/{id}")
    public User get(@PathVariable("id") Long id) {
        try {
            return userService.getUser(id);
        } catch (IllegalArgumentException e) {
            // 注意：这里不要把 e 作为 cause 传入。Spring 的 ExceptionHandlerExceptionResolver
            // 会沿异常 cause 链匹配 @ExceptionHandler，带上 IAE 会被全局 400 处理器抢走。
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
