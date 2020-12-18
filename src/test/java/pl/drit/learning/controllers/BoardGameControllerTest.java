package pl.drit.learning.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import pl.drit.learning.Main;
import pl.drit.learning.entity.BoardGameDetails;
import pl.drit.learning.entity.BoardGameDetailsList;

import javax.sql.DataSource;
import javax.ws.rs.core.Application;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"dev"})
@ContextConfiguration(classes = Main.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoardGameControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @BeforeEach
    void setup(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("before.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAllBoardgames() {
        BoardGameDetailsList response = restTemplate.getForObject(getRootUrl() + "/boardgames", BoardGameDetailsList.class);
        List<BoardGameDetails> boardGameDetailsList = response.getBoardGameDetailsList();
        for (BoardGameDetails boardgameDetails : boardGameDetailsList) {
            System.out.println(boardgameDetails.getId() + " - " + boardgameDetails.getTitle());
        }
        assertEquals(4, boardGameDetailsList.size());
    }

    @Test
    public void testGetBoardgameById() {
        BoardGameDetails boardGameDetails = restTemplate.getForObject(getRootUrl() + "/boardgames/1", BoardGameDetails.class);
        System.out.println(boardGameDetails.getTitle());
        assertEquals("Catan", boardGameDetails.getTitle());
        assertEquals("www.gryplanszowe.pl", boardGameDetails.getLink());
        assertEquals(99.00, boardGameDetails.getPrice());
    }

    @Test
    public void testUpdateBoardgame() {
        int id = 1;
        BoardGameDetails boardGameDetails = restTemplate.getForObject(getRootUrl() + "/boardgames/" + id, BoardGameDetails.class);
        BoardGameDetails updatedBoardgame = restTemplate.getForObject(getRootUrl() + "/boardgames/" + id, BoardGameDetails.class);
        updatedBoardgame.setTitle("Zmieniony tytuł");
        updatedBoardgame.setPrice(120L);
        restTemplate.put(getRootUrl() + "/boardgames/" + id, updatedBoardgame);
        assertEquals("Zmieniony tytuł", updatedBoardgame.getTitle());
        assertNotEquals(boardGameDetails.getTitle(), updatedBoardgame.getTitle());
    }

    @Test
    public void testDeleteBoardgame() {
        int id = 1;
        BoardGameDetails boardGameDetails = restTemplate.getForObject(getRootUrl() + "/boardgames/" + id, BoardGameDetails.class);
        assertNotNull(boardGameDetails);
        restTemplate.delete(getRootUrl() + "/boardgames/" + id);
        try {
            boardGameDetails = restTemplate.getForObject(getRootUrl() + "/boardgames/" + id, BoardGameDetails.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}