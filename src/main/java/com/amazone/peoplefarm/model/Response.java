package com.amazone.peoplefarm.model;

public class Response {

    boolean succes;
    private String message,exceptionName;
    private Exception exception;


    public Response() {
    }

    public Response(boolean succes) {
        this.succes = succes;
        this.message = "";
    }

    public Response(boolean succes, String message) {
        this(succes);
        this.message = message;
    }

    public Response(boolean succes, String message, Exception exception) {
        this(succes,message);
        this.exception = exception;
        this.exceptionName = exception.getClass().toString();
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
