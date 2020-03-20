package com.advance.exception;

import com.advance.i18n.I18nUtils;
import com.advance.reponse.BaseResponse;
import com.advance.reponse.BaseResponseBuilder;
import com.advance.reponse.DefaultErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 *
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);


    /**
     * get请求的参数校验失败
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    public BaseResponse handle(ConstraintViolationException exception) {
        logger.error("param validation error", exception);
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        if (!CollectionUtils.isEmpty(violations)) {
            String message = violations.iterator().next().getMessage();
            logger.error("validate error,message={0}", message);
            return BaseResponseBuilder.createResponse(DefaultErrorCode.VALIDATE_ERROR, message);
        }

        return BaseResponseBuilder.createResponse(DefaultErrorCode.ERROR);
    }

    /**
     * post请求校验失败
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    public BaseResponse handle(MethodArgumentNotValidException exception) {
        logger.error("param validation error", exception);
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        String code = DefaultErrorCode.VALIDATE_ERROR.getCode();
        String msg = null;
        if (!CollectionUtils.isEmpty(errors)) {
            int first = 0;
            for (int i = 0; i < errors.size(); i++) {
                ObjectError error = errors.get(i);
                String message = error.getDefaultMessage();
                String field = null;
                if (error instanceof FieldError) {
                    field = ((FieldError) error).getField();
                }

                message = I18nUtils.getMessage(message);
                if (i == first) {

                    //取一个错误信息传给前端
                    msg = message;
                }
                logger.error("validate error, field={0}, message={1}", field, message);
            }
        }
        return new BaseResponse(code, msg);
    }

    @ExceptionHandler
    public BaseResponse handle(CustomBusinessException e) {
        //errorCode处理
        String code = e.getCode();
        logger.error("business error", e);
        return BaseResponseBuilder.createResponse(code, e.getMessage(), e.getParams());
    }

    /**
     * 其他没有处理的错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public BaseResponse<Object> handle(Exception e) {
        logger.error("unkown error", e);
        return BaseResponseBuilder.createResponse(DefaultErrorCode.ERROR);
    }
}


