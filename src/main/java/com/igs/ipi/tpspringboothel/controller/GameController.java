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
        String showedName = game.getNom1();
        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView
                .addObject("game", game)
                .addObject("showedName", showedName);

        return modelAndView;
    }

    @GetMapping("/game")
    public ModelAndView game(){

        String showedName;

        GameModel game = partie.getGm();

        if (game.getPlayerTurn() == 1 ){
            showedName = game.getNom1();
        } else showedName = game.getNom2();


        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView
                .addObject("game", game)
                .addObject("showedName", showedName);
        return modelAndView;
    }


    @GetMapping("/game/drop/{id}")
    public String updateGame(@PathVariable Long id){

        int idz = id.intValue() - 1;

        GameModel game = partie.getGm();

        int playerId = game.getPlayerTurn();

        Integer[][] gameTable = game.getGameTable();
        for (int i = 0 ; i< 6 ; i++){
            if (gameTable[i][idz] == 1 || gameTable[i][idz] == 2) {
                gameTable[i - 1 ][idz] =  playerId;
                break;
            }
        }
        if (gameTable[5][idz] == 0){
            gameTable[5][idz] =  playerId;
        }

        if (game.getPlayerTurn() == 1 ){
            game.setPlayerTurn(2);
        } else game.setPlayerTurn(1);

        game.setGameTable(gameTable);
        partie.setGm(game);

        return "redirect:/game";
    }

}
