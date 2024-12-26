package com.fire.bigevent.jwtTest;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen() {
        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("id",1);
        claims.put("username","tangfire");
        // 生成jwt的代码
        String token = JWT.create()
                .withClaim("user",claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*12)) // 添加过期时间
                .sign(Algorithm.HMAC256("fireshine"));

        System.out.println(token);
    }


    @Test
    public void testParse() {
        // 定义字符串,模拟用户传递过来的token
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6InRhbmdmaXJlIn0sImV4cCI6MTczNTIyMTE4NH0.o6_DhyU5Z96QLD6hhc1Lc4g4LYPeljw7y9vHiAa9vvM";

        JWTVerifier decodeJWT = JWT.require(Algorithm.HMAC256("fireshine")).build();

        DecodedJWT jwt = decodeJWT.verify(token); // 验证token,生成一个解析后的JWT对象

        Map<String, Claim> claims = jwt.getClaims();

        System.out.println(claims.get("user"));

    }
}
