package com.amazone.peoplefarm.exceptions;

public class AccountNotFoundException extends AccountException{
    public AccountNotFoundException() {}

    public AccountNotFoundException(String message)
    {
        super(message);
    }
}
