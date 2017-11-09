package com.amazone.peoplefarm.models;

public class Response {

    boolean succes;

    public Response() {
    }

    public Response(boolean succes) {
        this.succes = succes;
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }
}
