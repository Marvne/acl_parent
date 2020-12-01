package com.ppt.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author 根据用户名生成token,token放到cookie中,根据用户名得到token
 *         根据token得到用户名
 * @version 1.0
 * @description: TODO
 * @date 2020/11/28 19:55
 */
public class TokenManager {

    //定义一个token的有效时长
    private long tokenEcpiration = 24*60*60*1000;
    //定义一个生成token的密钥
    private String tokenSignKey = "123456";

    /**
     * @description: 根据用户名生成token
     * @author yzp
     * @date 2020/11/28 21:03
     * @version 1.0
     */
    public String createToken(String username){
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenEcpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    //根据token获得用户信息
    public String getUserInfobyToken(String token){
        String userInfo = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return userInfo;
    }

    //删除token,其实就是不携带token
    public void  removeToken(String token){

    }
}
