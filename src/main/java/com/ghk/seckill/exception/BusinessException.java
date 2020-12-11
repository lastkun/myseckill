package com.ghk.seckill.exception;

import com.ghk.seckill.result.CodeMsg;

public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private CodeMsg codeMsg;


    public BusinessException(CodeMsg codeMsg) {
        this.codeMsg=codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
