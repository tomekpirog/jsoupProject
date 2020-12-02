package pl.drit.learning.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.drit.learning.entity.BoardGameDetails;
import pl.drit.learning.exceptions.ResourceNotFoundException;
import pl.drit.learning.repository.BoardGameRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class BoardGameController {

    @Autowired
    private BoardGameRepository boardGameRepository;

    public List<BoardGameDetails> getAllBoardgames() {
        return boardGameRepository.findAll();
    }

    public ResponseEntity<BoardGameDetails> getBoardGameById(Long boardGameId) throws ResourceNotFoundException {
        BoardGameDetails boardGameDetails = boardGameRepository.findById(boardGameId)
                .orElseThrow(() -> new ResourceNotFoundException("BoardGame not found on :: "+ boardGameId));
        return ResponseEntity.ok().body(boardGameDetails);
    }

    public ResponseEntity<BoardGameDetails> updateBoardGame(Long boardGameId, @RequestBody BoardGameDetails boardGameDetails) throws ResourceNotFoundException {
        BoardGameDetails boardGameDetail = boardGameRepository.findById(boardGameId)
                .orElseThrow(() -> new ResourceNotFoundException("BoardGame not found on :: "+ boardGameId));

        boardGameDetail.setTitle(boardGameDetail.getTitle());
        boardGameDetail.setLink(boardGameDetail.getLink());
        boardGameDetail.setPrice(boardGameDetail.getPrice());
        final BoardGameDetails updatedBoardGame = boardGameRepository.save(boardGameDetails);
        return ResponseEntity.ok(updatedBoardGame);
    }

    public Map<String, Boolean> deleteUser(Long boardGameId) throws Exception {
        BoardGameDetails boardGameDetails = boardGameRepository.findById(boardGameId)
                .orElseThrow(() -> new ResourceNotFoundException("BoardGame not found on :: "+ boardGameId));

        boardGameRepository.delete(boardGameDetails);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
