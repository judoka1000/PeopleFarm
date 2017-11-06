package com.amazone.peoplefarm.errors;

public class PersonNotFound extends Exception{
    public PersonNotFound() {}

    public PersonNotFound(String message)
    {
        super(message);
    }
}
