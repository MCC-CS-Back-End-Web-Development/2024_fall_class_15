package edu.mcc.tic_tac_toe.models;

import java.util.UUID;

public class Game {
    UUID id;
    String status = "In-Progress";
    String winner;

    int xWins;
    int oWins;
    int ties;

    String[][] board = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};

    public UUID getId() {
        return id;
    }

    public Game setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Game setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getWinner() {
        return winner;
    }

    public Game setWinner(String winner) {
        this.winner = winner;
        return this;
    }

    public int getxWins() {
        return xWins;
    }

    public Game setxWins(int xWins) {
        this.xWins = xWins;
        return this;
    }

    public int getoWins() {
        return oWins;
    }

    public Game setoWins(int oWins) {
        this.oWins = oWins;
        return this;
    }

    public int getTies() {
        return ties;
    }

    public Game setTies(int ties) {
        this.ties = ties;
        return this;
    }

    public String[][] getBoard() {
        return board;
    }

    public Game setBoard(String[][] board) {
        this.board = board;
        return this;
    }
}
