package com.amazone.peoplefarm.model;

public class Response<T> {

    boolean succes;
    private String exceptionName;
    private Exception exception;
    private T data;


    public Response() {
    }

    public Response(boolean succes) {
        this.succes = succes;
    }

    public Response(boolean succes, T object) {
        this(succes);
        this.data = object;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
