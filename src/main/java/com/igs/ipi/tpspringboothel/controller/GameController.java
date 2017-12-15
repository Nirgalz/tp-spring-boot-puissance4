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

public class GameController {

    private GameService gameService = new GameService();

    @Autowired
    private PartieEnCours partie;


    @GetMapping("/game/new")
    public ModelAndView newGame() {

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
    public ModelAndView game() {

        String showedName;

        GameModel game = partie.getGm();

        if (game.getPlayerTurn() == 1) {
            showedName = game.getNom1();
        } else showedName = game.getNom2();

        if (game.getWinner() != 0) {
            showedName = showedName + " is the winner";
        }


        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView
                .addObject("game", game)
                .addObject("showedName", showedName);
        return modelAndView;
    }


    @GetMapping("/game/drop/{id}")
    public String updateGame(@PathVariable Long id) {

        int idz = id.intValue() - 1;
        int[] moves = new int[2];


        GameModel game = partie.getGm();

        int playerId = game.getPlayerTurn();

        int[][] gameTable = game.getGameTable();
        for (int i = 0; i < 6; i++) {
            if (gameTable[i][idz] == 1 || gameTable[i][idz] == 2) {
                gameTable[i - 1][idz] = playerId;
                break;
            }
        }
        if (gameTable[5][idz] == 0) {
            gameTable[5][idz] = playerId;
        }

        if (game.getPlayerTurn() == 1) {
            game.setPlayerTurn(2);
        } else game.setPlayerTurn(1);

        //diagonals
        for (int x = 3; x < 6; x++) {
            for (int y = 3; y < 7; y++) {
                if (gameTable[x][y] != 0) {
                    if (gameTable[x - 1][y - 1] == gameTable[x][y]
                            && gameTable[x - 2][y - 2] == gameTable[x][y]
                            && gameTable[x - 3][y - 3] == gameTable[x][y]) {
                        game.setWinner(gameTable[x][y]);
                    }
                }
            }
        }
        for (int x = 3; x < 6; x++) {
            for (int y = 0; y < 4; y++) {
                if (gameTable[x][y] != 0) {
                    if (gameTable[x - 1][y + 1] == gameTable[x][y]
                            && gameTable[x - 2][y + 2] == gameTable[x][y]
                            && gameTable[x - 3][y + 3] == gameTable[x][y]) {
                        game.setWinner(gameTable[x][y]);
                    }
                }
            }
        }

        //horizontal
        for (int x = 3; x < 6; x++) {
            for (int y = 0; y < 7; y++) {
                if (gameTable[x][y] != 0) {
                    if (gameTable[x - 1][y] == gameTable[x][y]
                            && gameTable[x - 2][y] == gameTable[x][y]
                            && gameTable[x - 3][y] == gameTable[x][y]) {
                        game.setWinner(gameTable[x][y]);
                    }
                }
            }
        }
        //vertical
        for (int x = 0; x < 6; x++) {
            for (int y = 3; y < 7; y++) {
                if (gameTable[x][y] != 0) {
                    if (gameTable[x][y - 1] == gameTable[x][y]
                            && gameTable[x][y - 2] == gameTable[x][y]
                            && gameTable[x][y - 3] == gameTable[x][y]) {
                        game.setWinner(gameTable[x][y]);
                    }
                }
            }
        }


        game.setGameTable(gameTable);
        partie.setGm(game);

        if (game.getWinner() != 0){
            return "redirect:/game/won";
        }
        else return "redirect:/game";

    }

    @GetMapping("/game/won")
    public ModelAndView gameWon() {
        String winner = new String();
        GameModel game = partie.getGm();

        if (game.getWinner() ==1) {
            winner = game.getNom1();
        }
        else if (game.getWinner() ==2) {
            winner = game.getNom2();
        }


        ModelAndView modelAndView = new ModelAndView("won");
        modelAndView
                .addObject("winner", winner)
                .addObject("game", game);

        return modelAndView;
    }

}
