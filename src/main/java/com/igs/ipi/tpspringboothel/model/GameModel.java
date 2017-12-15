package com.igs.ipi.tpspringboothel.model;

import java.lang.reflect.Array;

public class GameModel {
    private String nom1;
    private String nom2;

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    private int winner;

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    private int playerTurn;


    private int[][] gameTable = new int[6][7];

    public int[][] getGameTable() {
        return gameTable;
    }

    public void setGameTable(int[][] gameTable) {
        this.gameTable = gameTable;
    }

    public String getNom1() {
        return nom1;
    }

    public void setNom1(String nom1) {
        this.nom1 = nom1;
    }

    public String getNom2() {
        return nom2;
    }

    public void setNom2(String nom2) {
        this.nom2 = nom2;
    }
}
