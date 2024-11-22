package edu.mcc.tic_tac_toe.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Move {
    @NotNull
    String gameId;

    @NotNull
    @Size(min=3, max = 3)
    String location;  //0,1

    @NotNull
    @Size(min=1, max = 1)
    String player; // X or O

    public String getGameId() {
        return gameId;
    }

    public Move setGameId(String gameId) {
        this.gameId = gameId;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Move setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getPlayer() {
        return player;
    }

    public Move setPlayer(String player) {
        this.player = player;
        return this;
    }
}
