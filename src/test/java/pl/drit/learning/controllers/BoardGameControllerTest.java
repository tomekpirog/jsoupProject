package pl.drit.learning.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import pl.drit.learning.Main;
import pl.drit.learning.entity.BoardGameDetails;

import javax.ws.rs.core.Application;

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

    @Test
    public void testGetAllEmployees() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/boardgames",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetEmployeeById() {
        BoardGameDetails boardGameDetails = restTemplate.getForObject(getRootUrl() + "/boardgames/1", BoardGameDetails.class);
        System.out.println(boardGameDetails.getTitle());
        assertNotNull(boardGameDetails);
    }

    @Test
    public void testCreateEmployee() {
        BoardGameDetails boardGameDetails = new BoardGameDetails();
        boardGameDetails.setTitle("Catan");
        boardGameDetails.setPrice(100L);
        boardGameDetails.setLink("https://www.aleplanszowki.pl");
        ResponseEntity<BoardGameDetails> postResponse = restTemplate.postForEntity(getRootUrl() + "/boardgames", boardGameDetails, BoardGameDetails.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateEmployee() {
        int id = 1;
        BoardGameDetails boardGameDetails = restTemplate.getForObject(getRootUrl() + "/boardgames/" + id, BoardGameDetails.class);
        boardGameDetails.setTitle("Dixit");
        boardGameDetails.setPrice(120L);
        restTemplate.put(getRootUrl() + "/boardgames/" + id, boardGameDetails);
        BoardGameDetails updatedEmployee = restTemplate.getForObject(getRootUrl() + "/boardgames/" + id, BoardGameDetails.class);
        assertNotNull(updatedEmployee);
    }

    @Test
    public void testDeleteEmployee() {
        int id = 2;
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