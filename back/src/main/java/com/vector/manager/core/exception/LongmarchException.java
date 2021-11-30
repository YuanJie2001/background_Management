package com.vector.manager.core.exception;

import lombok.Data;
import com.vector.manager.core.common.Result;

@Data
public class LongmarchException extends RuntimeException {

    private Integer code;
    private String message;

    public LongmarchException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public LongmarchException(String message) {
        this(Result.RESPOND_FAIL_CODE, message);
    }

}
