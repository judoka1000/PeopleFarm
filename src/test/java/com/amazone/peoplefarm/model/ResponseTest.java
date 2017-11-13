package com.amazone.peoplefarm.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.annotation.ApplicationScope;

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
