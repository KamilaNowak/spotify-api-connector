package com.nowak.spotifyapiconnector.controllers;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public String openMainPage(Model model){
        return "main";
    }

    @GetMapping("/error")
    public String showErrorPage(Model model){
        model.addAttribute("msg", "Something went wrong");
        return "main";
    }
}
