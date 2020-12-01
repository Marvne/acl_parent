package com.ppt.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yzp
 * @version 1.0
 * @description: TODO
 * @date 2020/11/29 14:02
 */
@Data
@ApiModel(description = "用户实体类")
public class User {

    private static final long serialVersionID = 1L;

    @ApiModelProperty(value = "微信openid")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "绰号")
    private String nickName;

    @ApiModelProperty(value = "用户头像")
    private String salt;

    @ApiModelProperty("用户签名")
    private String token;

}
