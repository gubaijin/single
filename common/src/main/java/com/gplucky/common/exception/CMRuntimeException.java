package com.gplucky.common.exception;

/**
 * Created by ehsy_it on 2017/1/23.
 */
public class CMRuntimeException extends RuntimeException {

    private String code;
    private String message;

    public CMRuntimeException(String message) {
        super(message);
        this.message = message;
    }

    public CMRuntimeException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CMRuntimeException(int code, String message) {
        super(message);
        this.code = String.valueOf(code);
        this.message = message;
    }

    public CMRuntimeException(final Throwable cause) {
        super(cause);
    }

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
