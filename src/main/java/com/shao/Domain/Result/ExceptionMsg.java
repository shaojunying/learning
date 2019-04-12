package com.shao.Domain.Result;

/**
 * Created by shao on 2019/4/3 10:08.
 */
public enum ExceptionMsg {
    SUCCESS("0000","操作成功"),
    LoginNameNotExists("0001","用户名不存在"),
    WrongPassword("0002","密码错误"),
    ExistingUsername("0003","用户名已存在"),
    WrongToken("0004","错误的Token"),
    FAILED("2000","操作失败");

    private String code;
    private String msg;


    ExceptionMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
