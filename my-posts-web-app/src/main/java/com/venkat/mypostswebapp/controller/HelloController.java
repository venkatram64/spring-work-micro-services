package com.venkat.mypostswebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/greetings")
    public String sayHello(Model model){
        String msg = "Hello, Venkatram ";
        model.addAttribute("message", msg);
        return "hello";
    }
}
