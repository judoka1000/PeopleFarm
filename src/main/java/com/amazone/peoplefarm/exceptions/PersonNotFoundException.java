package com.amazone.peoplefarm.exceptions;

public class PersonNotFoundException extends PersonException{
    public PersonNotFoundException() {}

    public PersonNotFoundException(String message)
    {
        super(message);
    }
}
