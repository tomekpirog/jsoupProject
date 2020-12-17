package pl.drit.learning.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import pl.drit.learning.Main;
import pl.drit.learning.entity.BoardGameDetails;
import pl.drit.learning.entity.BoardGameDetailsList;

import javax.ws.rs.core.Application;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    public void contextLoads() {

    }

    @Sql(scripts = "classpath:before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void testGetAllBoardgames() {
        BoardGameDetailsList response = restTemplate.getForObject(getRootUrl() + "/boardgames", BoardGameDetailsList.class);
        List<BoardGameDetails> boardGameDetailsList = response.getBoardGameDetailsList();
        for (BoardGameDetails boardgameDetails : boardGameDetailsList) {
            System.out.println(boardgameDetails);
        }
        /*ResponseEntity<String> result = restTemplate.getForEntity(getRootUrl() + "/boardgames", String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("boardgames"));*/

    }

    @Test
    public void testGetBoardgameById() {
        BoardGameDetails boardGameDetails = restTemplate.getForObject(getRootUrl() + "/boardgames/1", BoardGameDetails.class);
        System.out.println(boardGameDetails.getTitle());
        assertNotNull(boardGameDetails);
    }

    @Test
    public void testCreateBoardgame() {
        BoardGameDetails boardGameDetails = new BoardGameDetails();
        boardGameDetails.setTitle("Catan");
        boardGameDetails.setPrice(100L);
        boardGameDetails.setLink("https://www.aleplanszowki.pl");
        ResponseEntity<BoardGameDetails> postResponse = restTemplate.postForEntity(getRootUrl() + "/boardgames", boardGameDetails, BoardGameDetails.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateBoardgame() {
        int id = 1;
        BoardGameDetails boardGameDetails = restTemplate.getForObject(getRootUrl() + "/boardgames/" + id, BoardGameDetails.class);
        boardGameDetails.setTitle("Dixit");
        boardGameDetails.setPrice(120L);
        restTemplate.put(getRootUrl() + "/boardgames/" + id, boardGameDetails);
        BoardGameDetails updatedEmployee = restTemplate.getForObject(getRootUrl() + "/boardgames/" + id, BoardGameDetails.class);
        assertNotNull(updatedEmployee);
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