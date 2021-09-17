package com.stackify.sandbox;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }
    
    @RequestMapping("/orm")
    public String orm() {
        return "orm";
    }

    @RequestMapping("/slowdb")
    public String slowDB() {
        return "slowdb";
    }
    
    @RequestMapping("/slowrequest")
    public String slowRequest() {
        return "slowrequest";
    }

    @RequestMapping("/swallowedexception")
    public String swallowedException() {
        return "swallowedexception";
    }

    @RequestMapping("/untracked")
    public String untracked() {
        return "untracked";
    }
}
