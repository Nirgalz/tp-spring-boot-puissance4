package com.igs.ipi.tpspringboothel.controller;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/game")
public class GameController{

    @GetMapping("/new")
    public ModelAndView newGame(){
        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView
                .addObject("title", "sample")
                .addObject("body", "caca");

        return modelAndView;
    }
}
