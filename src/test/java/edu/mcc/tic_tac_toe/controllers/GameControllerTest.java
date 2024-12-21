package edu.mcc.tic_tac_toe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.mcc.tic_tac_toe.models.Game;
import edu.mcc.tic_tac_toe.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@DisplayName("Game Controller Test")
public class GameControllerTest {
    @InjectMocks
    private GameController subject;

    @Mock
    GameService gameService;

    private MockMvc mockMvc;
    private HttpHeaders httpHeaders;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        ReflectionTestUtils.setField(this, "httpHeaders", httpHeaders);
        ReflectionTestUtils.setField(this, "objectMapper", new ObjectMapper());
    }

    @Test
    @DisplayName("Get Game By ID - Success")
    void test_getGameByID_Success() throws Exception {
        //given
        Game mockGame = new Game();
        mockGame.setId(UUID.randomUUID());
        when(gameService.getGameById(any())).thenReturn(mockGame);

        // when
        MockHttpServletResponse resp = mockMvc.perform(
                get("/api/v1/games/{id}", mockGame.getId())
                        .headers(httpHeaders)
        ).andReturn().getResponse();


        // then
        assertThat(resp).isNotNull();
        assertThat(resp.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertTrue(resp.getContentAsString().contains(mockGame.getId().toString()));
    }

    @Test
    @DisplayName("Get Game All Games - Success")
    void test_getGameAllGames_Success() throws Exception {
        //given
        Game mockGame = new Game();
        mockGame.setId(UUID.randomUUID());
        when(gameService.getAllGames()).thenReturn(Collections.singletonList(mockGame));

        // when
        MockHttpServletResponse resp = mockMvc.perform(
                get("/api/v1/games")
                        .headers(httpHeaders)
        ).andReturn().getResponse();


        // then
        assertThat(resp).isNotNull();
        assertThat(resp.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Clear Game - Success")
    void test_clearGame_Success() throws Exception {
        //given
        Game mockGame = new Game();
        mockGame.setId(UUID.randomUUID());
        when(gameService.clearOrResetGame(mockGame.getId().toString(), false)).thenReturn(mockGame);

        MockHttpServletResponse response = mockMvc
                .perform(post("/api/v1/games/clear/{id}", mockGame.getId())
                        .headers(httpHeaders))
                .andReturn()
                .getResponse();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertTrue(response.getContentAsString().contains(mockGame.getId().toString()));
    }
    @Test
    @DisplayName("Clear Game - No ID - Not Found")
    void test_clearGame_notFound() throws Exception {

        MockHttpServletResponse response = mockMvc
                .perform(post("/api/v1/games/clear")
                        .headers(httpHeaders))
                .andReturn()
                .getResponse();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

}
