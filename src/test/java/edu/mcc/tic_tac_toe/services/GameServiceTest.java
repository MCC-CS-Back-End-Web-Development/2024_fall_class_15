package edu.mcc.tic_tac_toe.services;

import static org.assertj.core.api.Assertions.assertThat;

import edu.mcc.tic_tac_toe.models.Game;
import edu.mcc.tic_tac_toe.models.Move;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Game Service Test")
public class GameServiceTest {

    @InjectMocks
    private GameService subject;
    private final String[][] default_board = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};

    @Test
    @DisplayName("Create Game")
    void test_createGame(){
        // when
        Game game = subject.createGame();

        // then
        assertThat(game.getId()).isNotNull();
        assertThat(game.getStatus()).isEqualTo("In-Progress");
        assertThat(game.getBoard()).isEqualTo(default_board);
    }

    @Test
    @DisplayName("Reset Game")
    void test_reset_game(){
        // given
        Game game = subject.createGame();
        Move move = new Move();
        move.setGameId(game.getId().toString());
        move.setPlayer("X");
        move.setLocation("1,1");
        Game response = subject.makeMove(move);
        assertThat(response.getWinner()).isNull();
        assertThat(response.getBoard()).isNotEqualTo(default_board);
        move.setLocation("0,1");
        response =subject.makeMove(move);
        assertThat(response.getWinner()).isNull();
        assertThat(response.getBoard()).isNotEqualTo(default_board);
        move.setLocation("2,1");
        response =subject.makeMove(move);
        assertThat(response.getWinner()).isNotNull();
        assertThat(response.getWinner()).isEqualTo("X");
        assertThat(response.getBoard()).isNotEqualTo(default_board);
        assertThat(response.getxWins()).isEqualTo(1);


        // when
        Game resetResponse = subject.clearOrResetGame(response.getId().toString(), true);

        // then
        assertThat(resetResponse.getId()).isNotNull();
        assertThat(resetResponse.getStatus()).isEqualTo("In-Progress");
        assertThat(resetResponse.getBoard()).isEqualTo(default_board);
        assertThat(resetResponse.getxWins()).isEqualTo(0);
    }

    @Test
    @DisplayName("Delete Game - Not Found")
    void test_deleteGame_notFound(){
        // when
        Boolean deleteResponse = subject.deleteGame("123");

        // then
        assertThat(deleteResponse).isFalse();
    }

    @Test
    @DisplayName("Delete Game")
    void test_deleteGame(){
        // given
        Game game = subject.createGame();

        // when
       Boolean deleteResponse = subject.deleteGame(game.getId().toString());

        // then
        assertThat(deleteResponse).isTrue();
    }

    @Test
    @DisplayName("Reset Game - NO GO")
    void test_reset_game_bad(){
        // given
        Game game = subject.createGame();

        // when
        Game resetResponse = subject.clearOrResetGame(game.getId().toString(), true);

        // then
        assertThat(resetResponse.getStatus()).isEqualTo("In-Progress");
        assertThat(resetResponse.getBoard()).isEqualTo(default_board);
        assertThat(resetResponse.getxWins()).isEqualTo(0);
    }

}