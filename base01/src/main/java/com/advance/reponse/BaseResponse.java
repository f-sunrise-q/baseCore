package com.advance.reponse;

import lombok.Data;

/**
 * Created by qin on 20/3/19.
 */
@Data
public class BaseResponse<T> {

    private String code;

    private String msg;

    private T data;

    public BaseResponse() {
    }

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
