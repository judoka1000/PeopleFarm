package com.amazone.peoplefarm.exceptions;

public class PersonNotFoundException extends Exception{
    public PersonNotFoundException() {}

    public PersonNotFoundException(String message)
    {
        super(message);
    }
}
