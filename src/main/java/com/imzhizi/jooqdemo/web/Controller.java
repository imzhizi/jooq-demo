package com.imzhizi.jooqdemo.web;

import com.imzhizi.jooqdemo.domain.User;
import com.imzhizi.jooqdemo.domain.dto.Result;
import com.imzhizi.jooqdemo.domain.dto.UserDto;
import com.imzhizi.jooqdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class Controller {
    private final UserService userService;

    @Autowired
    public Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.allUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getOne(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users/login")
    public Result login(@RequestBody UserDto dto) {
        Optional<User> user = userService.getUserByUsername(dto.getUsername());
        if (!user.isPresent()) {
            return new Result(400, "账号不存在", null);
        } else {
            User u = user.get();
            if (!u.getPwd().equals(dto.getPwd())) {
                return new Result(400, "密码错误", null);
            } else {
                String token = UUID.randomUUID().toString();
                return new Result(200, "登录成功", token);
            }
        }
    }
}
