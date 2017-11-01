package com.hb.exception;

/*
 * @desc
 * @author lirb
 * @datetime 2017/10/17,11:50
 */
public class NotFindPersistenceException extends  PersistenceException{
    public static final ExceptionCode EXCEPTION_CODE = ExceptionCode.NOT_FIND_PERSISTENTCE;

    public NotFindPersistenceException(){}

    public NotFindPersistenceException(String message){
        super(message);
    }
}
