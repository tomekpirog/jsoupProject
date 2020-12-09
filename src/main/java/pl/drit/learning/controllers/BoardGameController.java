package pl.drit.learning.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.drit.learning.entity.BoardGameDetails;
import pl.drit.learning.exceptions.ResourceNotFoundException;
import pl.drit.learning.repository.BoardGameRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BoardGameController {

    @Autowired
    private BoardGameRepository boardGameRepository;

    @GetMapping("/boardgames")
    public List<BoardGameDetails> getAllBoardgames() {
        return boardGameRepository.findAll();
    }

    @GetMapping("/boardgames/{id}")
    public ResponseEntity<BoardGameDetails> getBoardGameById(
            @PathVariable(value = "id") Long boardGameId) throws ResourceNotFoundException {
        BoardGameDetails boardGameDetails = boardGameRepository.findById(boardGameId)
                .orElseThrow(() -> new ResourceNotFoundException("BoardGame not found on :: "+ boardGameId));
        return ResponseEntity.ok().body(boardGameDetails);
    }

    @PatchMapping("/boardgames/{id}")
    public ResponseEntity<BoardGameDetails> updateBoardGame(@PathVariable(value = "id") Long boardGameId, @Valid @RequestBody BoardGameDetails boardGameDetails) throws ResourceNotFoundException {
        BoardGameDetails found = boardGameRepository.findById(boardGameId)
                .orElseThrow(() -> new ResourceNotFoundException("BoardGame not found on :: "+ boardGameId));

        found.setTitle(boardGameDetails.getTitle());
        found.setLink(boardGameDetails.getLink());
        found.setPrice(boardGameDetails.getPrice());
        final BoardGameDetails updatedBoardGame = boardGameRepository.save(found);
        return ResponseEntity.ok(updatedBoardGame);
    }

    @DeleteMapping("/boardgames/{id}")
    public Map<String, Boolean> deleteBoardGame(
            @PathVariable(value = "id") Long boardGameId) throws Exception {
        BoardGameDetails boardGameDetails = boardGameRepository.findById(boardGameId)
                .orElseThrow(() -> new ResourceNotFoundException("BoardGame not found on :: "+ boardGameId));

        boardGameRepository.delete(boardGameDetails);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
