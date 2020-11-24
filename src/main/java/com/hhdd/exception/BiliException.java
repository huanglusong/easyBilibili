package com.hhdd.exception;

/**
 * @Author HuangLusong
 * @Date 2020/11/16 16:02
 */
public class BiliException extends RuntimeException {
    public BiliException() {
        super();
    }

    public BiliException(String msg) {
        super(msg);
    }
    public BiliException(String msg,Throwable cause){
        super(msg,cause);
    }


}
