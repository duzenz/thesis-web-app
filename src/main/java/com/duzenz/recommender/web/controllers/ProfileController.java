package com.duzenz.recommender.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileController {

    @RequestMapping("profile")
    public String profile(Model model) {
        return "profile";
    }

}
