package edu.mcc.tic_tac_toe.services;

import edu.mcc.tic_tac_toe.models.Game;
import edu.mcc.tic_tac_toe.models.Move;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {
    List<Game> games = new ArrayList<>();

    public Game createGame(){
        Game game = new Game();
        game.setId(UUID.randomUUID());
        games.add(game);
        return game;
    }

    public List<Game> getAllGames(){
        return games;
    }

    public Game getGameById(String id){
        return games.stream().filter(game -> game.getId().toString().equals(id)).findFirst().orElse(null);
    }

    public Game clearOrResetGame(String id, Boolean reset){
        Game game = games.stream().filter(filterGame -> filterGame.getId().toString().equals(id)).findFirst().orElse(null);
        if(game != null){
            games.remove(game);

            String[][] freshBoard = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
            game.setBoard(freshBoard);
            game.setWinner(null);
            game.setStatus("In-Progress");

            if(reset){
                game.setTies(0);
                game.setoWins(0);
                game.setxWins(0);
            }

            games.add(game);
        }

        return game;
    }

    public Boolean deleteGame(String id){
        Game game = games.stream().filter(filterGame -> filterGame.getId().toString().equals(id)).findFirst().orElse(null);
        if(game != null){
            games.remove(game);
            return true;
        }
        return false;
    }

    public Game makeMove(Move request){
        Game game = games.stream().filter(filterGame -> filterGame.getId().toString().equals(request.getGameId())).findFirst().orElse(null);
        if(game != null){
            games.remove(game);

            String[][] board = game.getBoard();
            board[Integer.parseInt(request.getLocation().split(",")[0])][Integer.parseInt(request.getLocation().split(",")[1])] = request.getPlayer().toUpperCase();
            game.setBoard(board);

            String winner = checkWinner(game.getBoard());
            if(winner.equalsIgnoreCase("Player X wins!")){
                game.setxWins(game.getxWins() + 1);
                game.setWinner("X");
                game.setStatus("Complete");
            } else if(winner.equalsIgnoreCase("Player O wins!")){
                game.setoWins(game.getoWins() + 1);
                game.setWinner("O");
                game.setStatus("Complete");
            } else if(winner.equalsIgnoreCase("It's a tie!")){
                game.setTies(game.getTies() + 1);
                game.setWinner("TIE");
                game.setStatus("Complete");
            }


            games.add(game);
        }

        return game;
    }

    private String checkWinner(String[][] board) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].equals(" ")) {
                return "Player " + board[i][0] + " wins!";
            }
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].equals(" ")) {
                return "Player " + board[0][i] + " wins!";
            }
        }

        // Check diagonals
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals(" ")) {
            return "Player " + board[0][0] + " wins!";
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals(" ")) {
            return "Player " + board[0][2] + " wins!";
        }

        // Check if the game is finished or still ongoing
        boolean full = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(" ")) {
                    full = false;
                    break;
                }
            }
            if (!full) {
                break;
            }
        }
        if (full) {
            return "It's a tie!";
        }

        // No winner yet
        return "No winner yet";
    }
}
