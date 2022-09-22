package com.cmgzs;

import lombok.Getter;

@Getter
public class RsaException extends RuntimeException {
    private final String message;
    public RsaException(String message) {
        this.message = message;
    }
}