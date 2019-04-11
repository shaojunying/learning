package com.shao.Domain.Result;

/**
 * Created by shao on 2019/4/3 10:09.
 */
public class ResponseData {

    private String rspCode="0000";

    private String rspMsg = "操作成功";

    public ResponseData() {
    }

    public ResponseData(String rspCode, String rspMsg) {
        this.rspCode = rspCode;
        this.rspMsg = rspMsg;
    }

    public ResponseData(ExceptionMsg msg){
        this.rspCode=msg.getCode();
        this.rspMsg=msg.getMsg();
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }
}
