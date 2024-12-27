package com.fire.bigevent.controller;

import ch.qos.logback.core.util.StringUtil;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "用户相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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

            // 把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

            operations.set(token,token,12, TimeUnit.HOURS);


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


    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        // 1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }

        // 原密码是否正确
        // 调用userService根据用户名拿到原密码，再和old_pwd对比
        Map<String,Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUserName(username);
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码填写不正确");
        }

        // newPwd和rePwd是否一样
        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码不正确");
        }

        // 2.调用service完成密码更新

        userService.updatePwd(newPwd);

        // 删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }



}
