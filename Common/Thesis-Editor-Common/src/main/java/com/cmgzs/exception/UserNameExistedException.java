package com.cmgzs.exception;

/**
 * 用户名已存在异常
 */
public class UserNameExistedException extends RuntimeException {

    public UserNameExistedException() {
        super("该用户名已存在");
    }

    public UserNameExistedException(String message) {
        super(message);
    }
}
