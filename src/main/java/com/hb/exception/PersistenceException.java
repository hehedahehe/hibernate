package com.hb.exception;

/*
 * @desc
 * @author lirb
 * @datetime 2017/10/17,11:56
 */
public class PersistenceException extends  RuntimeException{

    private String exceptionMessage;

    public PersistenceException(){
        super();
    }

    public PersistenceException(String message){
        super(message);
        this.exceptionMessage = message;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
