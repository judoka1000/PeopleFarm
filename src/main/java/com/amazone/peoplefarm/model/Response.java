package com.amazone.peoplefarm.model;

public class Response {

    boolean succes;
    private String exceptionName;
    private Exception exception;


    public Response() {
    }

    public Response(boolean succes) {
        this.succes = succes;
    }

    public Response(boolean succes, Exception exception) {
        this(succes);
        this.exception = exception;
        this.setExceptionName(exception.getClass().toString());
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }
}
