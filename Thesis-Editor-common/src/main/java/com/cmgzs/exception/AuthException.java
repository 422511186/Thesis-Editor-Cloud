package com.cmgzs.exception;

/**
 * @author huangzhenyu
 * @date 2022/9/20
 */
public class AuthException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public AuthException(String message)
    {
        this.message = message;
        this.code = 500;
    }

    public AuthException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public AuthException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
        this.code = 500;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }
}
