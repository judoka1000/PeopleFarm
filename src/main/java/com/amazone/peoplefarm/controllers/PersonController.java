package com.amazone.peoplefarm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PersonController {

    @RequestMapping(value = "/hoi")
    public String zegHoi(){
        return "hoi";
    }

    @RequestMapping(value = "/maarten")
    public String maarten(){
        return "maarten";
    }

}
