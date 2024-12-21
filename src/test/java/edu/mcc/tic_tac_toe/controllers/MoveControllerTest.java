package edu.mcc.tic_tac_toe.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.mcc.tic_tac_toe.models.Game;
import edu.mcc.tic_tac_toe.models.Move;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@DisplayName("Move Controller Test")
public class MoveControllerTest {

    @InjectMocks
    private MoveController subject;

    @Mock
    private GameService gameService;

    private MockMvc mockMvc;
    private HttpHeaders httpHeaders;
    private ObjectMapper mapper;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ReflectionTestUtils.setField(this, "httpHeaders", httpHeaders);
        ReflectionTestUtils.setField(this, "mapper", new ObjectMapper());
    }

    @Test
    @DisplayName("Make Move - No Body")
    void test_makeMove_emptyBody() throws Exception {

        MockHttpServletResponse response = mockMvc
                .perform(post("/api/v1/moves")
                        .headers(httpHeaders))
                .andReturn()
                .getResponse();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Make Move - Invalid Body - Max Location Overflow")
    void test_makeMove_maxLocationOverflow() throws Exception {

        Move move = new Move();
        move.setGameId(UUID.randomUUID().toString());
        move.setLocation("123456");
        move.setPlayer("X");

        MockHttpServletResponse response = mockMvc
                .perform(post("/api/v1/moves")
                        .headers(httpHeaders)
                        .content(mapper.writeValueAsString(move)))
                .andReturn()
                .getResponse();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Make Move - Success")
    void test_successfulMakeMove() throws Exception {

        when(gameService.makeMove(any())).thenReturn(new Game());

        Move move = new Move();
        move.setGameId(UUID.randomUUID().toString());
        move.setLocation("123");
        move.setPlayer("X");

        MockHttpServletResponse response = mockMvc
                .perform(post("/api/v1/moves")
                        .headers(httpHeaders)
                        .content(mapper.writeValueAsString(move)))
                .andReturn()
                .getResponse();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
