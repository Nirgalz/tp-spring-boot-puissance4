package com.igs.ipi.tpspringboothel.controller;


import com.igs.ipi.tpspringboothel.PartieEnCours;
import com.igs.ipi.tpspringboothel.model.GameModel;
import com.igs.ipi.tpspringboothel.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class GameController{

    private GameService gameService = new GameService();

    @Autowired
    private  PartieEnCours partie;



    @GetMapping("/game/new")
    public ModelAndView newGame(){

        GameModel game = gameService.newGame();

        partie.setGm(game);

        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView
                .addObject("game", game);

        return modelAndView;
    }

    @GetMapping("/game")
    public ModelAndView game(){


        GameModel game = partie.getGm();

        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView
                .addObject("game", game)
                .addObject("gameTable", game.getGameTable());
        return modelAndView;
    }


    @GetMapping("/game/drop/{id}")
    public String updateGame(@PathVariable Long id){

        int idz = id.intValue() - 1;

        GameModel game = partie.getGm();
        Integer[][] gameTable = game.getGameTable();
        for (int i = 0 ; i< 6 ; i++){
            if (gameTable[5][idz] == 0){
                gameTable[5][idz] = 1;
            }else
            if (gameTable[i][idz] == 1) {
                gameTable[i - 1 ][idz] = 1;
            }
        }


        game.setGameTable(gameTable);
        partie.setGm(game);

        return "redirect:/game";
    }

}
