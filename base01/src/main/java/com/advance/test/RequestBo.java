package com.advance.test;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by qin on 20/3/20.
 */
@Data
public class RequestBo {

    @NotEmpty(message = "param1.empty.error")
    private String param1;

    private Integer param2;
}
