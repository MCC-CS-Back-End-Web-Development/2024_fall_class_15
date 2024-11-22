package edu.mcc.tic_tac_toe.controllers;

import edu.mcc.tic_tac_toe.models.Game;
import edu.mcc.tic_tac_toe.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Game> createGame(){
        return ResponseEntity.ok(gameService.createGame());
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames(){
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable String id){
        Game game = gameService.getGameById(id);
        return game != null ? ResponseEntity.ok(game) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/clear/{id}")
    public ResponseEntity<Game> clearGameById(@PathVariable String id){
        Game game = gameService.clearOrResetGame(id, false);
        return game != null ? ResponseEntity.ok(game) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/reset/{id}")
    public ResponseEntity<Game> resetGameById(@PathVariable String id){
        Game game = gameService.clearOrResetGame(id, true);
        return game != null ? ResponseEntity.ok(game) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Game> deleteGameById(@PathVariable String id){
        return gameService.deleteGame(id) ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) :  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}