package com.video.model.req;

import lombok.Data;


@Data
public class LoginReq {

    /**
     * 1、邮箱 2、手机号
     */
    private Integer type;

    /**
     * 账户
     */
    private String account;

    /**
     * 密码
     */
    private String pwd;

}
