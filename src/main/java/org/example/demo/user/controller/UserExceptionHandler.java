package org.example.demo.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 全局异常处理。
 * <p>
 * {@code @RestControllerAdvice} = {@code @ControllerAdvice} + {@code @ResponseBody}，
 * 对所有 {@code @RestController} 生效，把业务异常统一转换成 JSON 错误响应，
 * 避免把堆栈直接暴露给调用方。
 * <p>
 * 若不处理 {@code ResponseStatusException}，它会被 {@code ResponseStatusExceptionResolver}
 * 交给 Servlet 容器，返回 Tomcat 的 HTML 错误页；这里统一改成 JSON。
 */
@RestControllerAdvice
public class UserExceptionHandler {

    /** 业务校验失败（如用户名为空、年龄非法）→ 400 */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
        return buildResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /** 控制器主动抛出的状态异常（如查询不到用户 → 404） */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException e) {
        return buildResponse(e.getStatusCode().value(), e.getReason());
    }

    private ResponseEntity<Map<String, Object>> buildResponse(int status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status);
        body.put("error", HttpStatus.valueOf(status).getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
