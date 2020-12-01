package com.ppt.security.security;

import com.ppt.utils.utils.R;
import com.ppt.utils.utils.ResponseUtil;
import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author yzp
 * @version 1.0
 * @description: 退出要做什么(token是通过每一次发动请求的header传过来的)删除token,删除redis的token信息
 * @date 2020/11/28 20:14
 */
@Component
public class  TokenLogoutHandler implements LogoutHandler {

    //其他类调用这个类的的方法的时候需要用到这两个参数,所以能把这个两个参数携带过去的方式就是通过构造具有这两个参数的构造函数
    public TokenManager tokenManager;

    public RedisTemplate redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager,RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;
    }


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        //从header里获得此次的token,然后在redis里面移除相关信息
        String token = request.getHeader("token");
        if(token != null){
            //没什么意义
            tokenManager.removeToken(token);
            String userName = tokenManager.getUserInfobyToken(token);
            redisTemplate.delete(userName);
        }
        ResponseUtil.out(response, R.ok());
    }
}
