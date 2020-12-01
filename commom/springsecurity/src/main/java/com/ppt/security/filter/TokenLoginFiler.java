package com.ppt.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppt.security.entity.SecurityUser;
import com.ppt.security.entity.User;
import com.ppt.security.security.TokenManager;
import com.ppt.utils.utils.R;
import com.ppt.utils.utils.ResponseUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author yzp
 * @version 1.0
 * @description: 处理认证的过滤器
 * @date 2020/11/29 11:13
 */
public class TokenLoginFiler extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;
    //得到表单提交过来的用户名和密码进行一系列的认证处理
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //获得表单提交的数据
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),new ArrayList<>()));
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
    /**
     * @description: 认证成功后
     * @author yzp
     * @date 2020/11/30 22:04
     * @version 1.0
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //认证成功后,得到认证成功的用户对象
        SecurityUser user = (SecurityUser) authResult.getPrincipal();
        //根据用户信息生成token
        String token = tokenManager.createToken(user.getCurrentUserInfo().getUsername());
        //将用户和用户权限列表放到redis里
        redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(),user.getPermissionValueList());
        //返回一个token
        ResponseUtil.out(response,R.ok().data("token",token));
   }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //返回一个错误的对象
        ResponseUtil.out(response, R.error());
    }
}
