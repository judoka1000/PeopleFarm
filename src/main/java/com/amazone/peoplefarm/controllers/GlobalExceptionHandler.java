package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.exceptions.AccountException;
import com.amazone.peoplefarm.exceptions.AccountNotFoundException;
import com.amazone.peoplefarm.exceptions.GameStateNotFoundException;
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

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response defaultErrorHandler(HttpServletResponse httpServletResponse, Exception e) throws Exception {
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new Response<>(false, e);
    }

    @ResponseBody
    @ExceptionHandler(AccountException.class)
    public Response accountExceptionHandler(HttpServletResponse httpServletResponse, Exception e) throws Exception {
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new Response<>(false, e);
    }

    @ResponseBody
    @ExceptionHandler(GameStateNotFoundException.class)
    public Response GameStateNotFoundExceptionHandler(HttpServletResponse httpServletResponse, Exception e) throws Exception {
        httpServletResponse.setStatus(498);
        return new Response<>(false, e);
    }
    @ResponseBody
    @ExceptionHandler(AccountNotFoundException.class)
    public Response AccountNotFoundExceptionHandler(HttpServletResponse httpServletResponse, Exception e) throws Exception {
        httpServletResponse.setStatus(498);
        return new Response<>(false, e);
    }

}
