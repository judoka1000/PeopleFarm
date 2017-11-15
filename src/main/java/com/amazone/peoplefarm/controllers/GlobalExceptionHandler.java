package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.exceptions.AccountException;
import com.amazone.peoplefarm.models.Response;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(AccountException.class)
//    public void handleAccountExc() {
//    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response defaultErrorHandler(HttpServletResponse httpServletResponse, Exception e) throws Exception {
        httpServletResponse.setStatus(HttpServletResponse.SC_CONFLICT);
        return new Response<>(false, e);
    }

    @ResponseBody
    @ExceptionHandler(AccountException.class)
    public Response accountExceptionHandler(HttpServletResponse httpServletResponse, Exception e) throws Exception {
        httpServletResponse.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        return new Response<>(false, e);
    }
}
