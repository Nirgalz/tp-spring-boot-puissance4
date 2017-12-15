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


        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (gameTable[i][j] == 1) {
                    moves[0] = 1;
                    moves[1]++;
                } else if (gameTable[i][j] == 2) {
                    moves[0] = 2;
                    moves[1]++;
                }
                if (moves[1] == 4) {
                    break;
                } else if (j == 6) {
                    moves[0] = 0;
                    moves[1] = 0;
                }
            }
        }


        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (gameTable[j][i] == 1) {
                    moves[0] = 1;
                    moves[1]++;
                } else if (gameTable[j][i] == 2) {
                    moves[0] = 2;
                    moves[1]++;
                }
                if (moves[1] == 4) {
                    break;
                } else if (i == 5) {
                    moves[0] = 0;
                    moves[1] = 0;
                }
            }
        }

        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 7; j++) {
                if (gameTable[i][j] != 0){
                    if (gameTable[i][j] == 1) {
                        if (gameTable[i-1][j-1] == gameTable[i][j]
                                && gameTable[i-2][j-2] == gameTable[i][j]
                                && gameTable[i-3][j-3] == gameTable[i][j])
                        {
                            game.setWinner(1);
                        }

                    } else if (gameTable[i][j] == 2) {
                        if (gameTable[i-1][j-1] == gameTable[i][j]
                                && gameTable[i-2][j-2] == gameTable[i][j]
                                && gameTable[i-3][j-3] == gameTable[i][j])
                        {
                            game.setWinner(2);
                        }
                    }
                }
            }
        }



        if (moves[1] == 4) {
            game.setWinner(moves[0]);
        }


        game.setGameTable(gameTable);
        partie.setGm(game);

        return "redirect:/game";
    }

}
