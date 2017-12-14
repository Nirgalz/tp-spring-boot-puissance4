package com.igs.ipi.tpspringboothel.service;

import com.igs.ipi.tpspringboothel.model.GameModel;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public GameModel newGame(){
        GameModel game = new GameModel();
        game.setNom1("Alfred");
        game.setNom2("René");

        return game;
    }
}
