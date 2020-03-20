package com.advance.test;

import com.advance.reponse.IErrorCode;

/**
 * 默认错误码
 *
 * Created by qin on 20/3/19.
 */
public enum BusinessErrorCode implements IErrorCode{

    BUSINESS_ERROR_1("20001", "business.error.test"),

   ;

    private String code;

    private String message;

    BusinessErrorCode(String code, String message){
        this.code = code;
        this.message= message;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
}
