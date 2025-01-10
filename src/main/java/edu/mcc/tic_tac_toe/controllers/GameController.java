package edu.mcc.tic_tac_toe.controllers;

import edu.mcc.tic_tac_toe.models.Game;
import edu.mcc.tic_tac_toe.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Game APIs")
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
    @Operation(
            description = "Get all games",
            summary = "API to list out all of the active games",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Game.class))
                    )
            }
    )
    public ResponseEntity<List<Game>> getAllGames(){
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get game by ID",
            summary = "This endpoint returns a game, if found, by it's ID",
            parameters = @Parameter(
                    name = "ID",
                    description = "The id of the game you want to find",
                    example= "97de1d18-13f9-45e2-9da9-fd3e24f90a52"
            ),
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Game.class))
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(schema = @Schema(example = " "))
                    )
            }
    )
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
