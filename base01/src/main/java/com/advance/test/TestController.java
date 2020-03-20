package com.advance.test;

import com.advance.exception.CustomBusinessException;
import com.advance.reponse.BaseResponse;
import com.advance.reponse.BaseResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by qin on 20/3/19.
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping("/test1")
    public BaseResponse test1(){
        return BaseResponseBuilder.createResponse();
    }

    @PostMapping("/test2")
    public BaseResponse test2(@RequestBody @Valid RequestBo requestBo){
        return BaseResponseBuilder.createResponse();
    }

    @GetMapping("/test3")
    public BaseResponse test3(){
        throw new CustomBusinessException(BusinessErrorCode.BUSINESS_ERROR_1);
    }
}
