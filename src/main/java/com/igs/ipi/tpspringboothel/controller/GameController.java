package com.igs.ipi.tpspringboothel.controller;


import com.igs.ipi.tpspringboothel.service.GameService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/game")
public class GameController{

    private GameService gameService = new GameService();

    @GetMapping("/new")
    public ModelAndView newGame(){
        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView
                .addObject("game", gameService.newGame())
                .addObject("gameTable", gameService.newGame().getGameTable());

        return modelAndView;
    }
}
