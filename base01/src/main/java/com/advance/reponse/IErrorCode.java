package com.advance.reponse;

/**
 * 系统错误码的抽象定义
 *
 * Created by qin on 20/3/19.
 */
public interface IErrorCode {

    /**
     * 获取错误码
     * @return
     */
    String getCode();

    /**
     * 获取错误信息
     * @return
     */
    String getMessage();
}
