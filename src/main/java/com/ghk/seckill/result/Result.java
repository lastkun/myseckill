package com.ghk.seckill.result;

/**
 *统一封装返回给前端的信息
 */
public class Result<E> {
    private int code;
    private String msg;
    private E data;

    private Result(E data) {
        this.code=0;
        this.msg="success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg == null)
            return;
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    /**
     * 成功时候调用
     */
    public static <E> Result<E> success(E data){
        return new Result<E>(data);
    }

    /**
     * 失败时调用
     */
    public static <E> Result<E> fail(CodeMsg codeMsg){
        return new Result<E>(codeMsg);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
