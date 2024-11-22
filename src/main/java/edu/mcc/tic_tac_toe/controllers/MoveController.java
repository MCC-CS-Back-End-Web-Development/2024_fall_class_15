package edu.mcc.tic_tac_toe.controllers;

import edu.mcc.tic_tac_toe.models.Game;
import edu.mcc.tic_tac_toe.models.Move;
import edu.mcc.tic_tac_toe.services.GameService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/moves")
public class MoveController {

    private final GameService gameService;

    public MoveController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Game> makeMove(@RequestBody @Valid Move move){
        Game game = gameService.makeMove(move);
        return game != null ? ResponseEntity.ok(game) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
