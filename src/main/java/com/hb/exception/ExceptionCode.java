package com.hb.exception;/*
 * @desc
 * @author lirb
 * @datetime 2017/10/17,11:51
 */

public enum ExceptionCode {
    //未找到持久化实例
    NOT_FIND_PERSISTENTCE(1);

    private int value;

    ExceptionCode(int code){
        this.value =code;
    }

    public int getValue() {
        return this.value;
    }
}
