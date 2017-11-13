package com.amazone.peoplefarm.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseTest<T> {

    private Response<T> response;

    @Before
    public void beforeMethod(){
        response = new Response<>();
    }

    @Test
    public void isSucces(){
        response.setSucces(true);
        assertEquals(true, response.isSucces());
        response.setSucces(false);
        assertEquals(false, response.isSucces());
    }


}
