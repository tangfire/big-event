package com.fire.bigevent.controller;

import com.fire.bigevent.pojo.Result;
import com.fire.bigevent.pojo.User;
import com.fire.bigevent.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "用户相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result register(String username, String password){
        // 查询用户
        User u =  userService.findByUserName(username);
        if (u == null){
            userService.register(username,password);
            return Result.success();
        }else{
            return Result.error("用户名已被占用");
        }
    }

}
