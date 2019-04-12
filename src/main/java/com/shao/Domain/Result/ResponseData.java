package com.shao.Domain.Result;

/**
 * Created by shao on 2019/4/3 10:09.
 */
public class ResponseData {

    private String rspCode="0000";

    private String rspMsg = "操作成功";

    private Object data;

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
    public ResponseData(ExceptionMsg msg,Object data){
        this.rspCode=msg.getCode();
        this.rspMsg=msg.getMsg();
        this.data = data;
    }

    public ResponseData(Object data) {
        this(ExceptionMsg.SUCCESS,data);
    }

    public ResponseData(String rspCode, String rspMsg, Object data) {
        this.rspCode = rspCode;
        this.rspMsg = rspMsg;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
