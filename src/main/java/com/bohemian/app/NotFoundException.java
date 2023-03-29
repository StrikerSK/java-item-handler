package com.bohemian.app;

public class NotFoundException extends RuntimeException {

    public NotFoundException (String msg) {
        super(msg);
    }

    public NotFoundException (String msg, Throwable e) {
        super(msg, e);
    }

}
