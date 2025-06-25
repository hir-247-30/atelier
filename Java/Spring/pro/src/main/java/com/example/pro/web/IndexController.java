package com.example.pro.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "<h3>Proを目指すSpring入門</h3>";
    }
}
