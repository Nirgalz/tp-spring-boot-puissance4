package com.igs.ipi.tpspringboothel.service;

import com.igs.ipi.tpspringboothel.model.GameModel;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;

@Service
public class GameService {

    public GameModel newGame(){
        GameModel game = new GameModel();
        game.setNom1("Alfred");
        game.setNom2("René");
        game.setPlayerTurn(1);
        int[][] gameTab = new int[6][7];

        for (int i = 0 ; i < 6 ; i++){
            for (int j = 0 ; j < 7 ; j++){
                gameTab[i][j] = 0;
            }
        }


        game.setGameTable(gameTab);

        return game;
    }
}
