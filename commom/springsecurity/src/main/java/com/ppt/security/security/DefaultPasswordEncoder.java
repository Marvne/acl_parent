package com.ppt.security.security;

import com.ppt.utils.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



/**
 * @author yzp
 * @version 1.0
 * @description: 生成密码的默认处理器
 * @date 2020/11/28 19:44
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    public  DefaultPasswordEncoder(){
        this(-1);
    }
    public DefaultPasswordEncoder(int length){

    }
    /**
     * @description: 对密码加密操作
     * @author yzp
     * @date 2020/11/28 20:32
     * @version 1.0
     */
    @Override
    public String encode(CharSequence charSequence) {
        return  MD5.encrypt(charSequence.toString());
    }

    /**
     * @description: 将传过来的密码与已有的密码进行匹配看看是否相等
     * @author yzp
     * @date 2020/11/28 21:00
     * @version 1.0
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(MD5.encrypt(charSequence.toString()));
    }

}
