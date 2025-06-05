package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "<h3>スプリング起動完了</h3>";
    }
}
