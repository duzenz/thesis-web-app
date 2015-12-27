package com.duzenz.recommender.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("/adminprofile")
    public String profile(Model model) {
        return "adminprofile";
    }

}
