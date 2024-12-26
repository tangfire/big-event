package com.fire.bigevent.controller;

import com.fire.bigevent.pojo.Result;
import com.fire.bigevent.pojo.User;
import com.fire.bigevent.service.UserService;
import com.fire.bigevent.utils.JwtUtil;
import com.fire.bigevent.utils.Md5Util;
import com.fire.bigevent.utils.ThreadLocalUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.io.ResolverUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "用户相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        // 查询用户
        User u =  userService.findByUserName(username);
        if (u == null){
            userService.register(username,password);
            return Result.success();
        }else{
            return Result.error("用户名已被占用");
        }
    }


    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        // 根据用户名查询用户
        User u = userService.findByUserName(username);
        // 判断该用户是否存在
        if (u == null){
            return Result.error("用户名或密码错误");
        }

        // 判断密码是否正确
        if(Md5Util.getMD5String(password).equals(u.getPassword())){
            // 登录成功
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",u.getId());
            claims.put("username",u.getUsername());
            String token = JwtUtil.genToken(claims);

            return Result.success(token);
        }

        return Result.error("用户名或密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        Map<String,Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");

        User user = userService.findByUserName(username);

        return Result.success(user);
    }


    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);

        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }



}
