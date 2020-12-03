package pl.drit.learning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.drit.learning.dto.ParsableSiteDTO;
import pl.drit.learning.dto.SearchResultDTO;
import pl.drit.learning.entity.BoardGameDetails;
import pl.drit.learning.parsers.WebsiteParser;
import pl.drit.learning.repository.BoardGameRepository;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@RestController
public class MyController {

    @Autowired
    private BoardGameRepository boardGameRepository;

    @GetMapping(value = "/")
    public String home() {
        return "home page";
    }

    @Produces(MediaType.APPLICATION_JSON)
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/boardgame/{title}")
    public Set<SearchResultDTO> getBoardgameByName(@PathVariable("title") String title) throws IOException, ParseException {
        Set<SearchResultDTO> result = new TreeSet<>(Comparator.comparing(SearchResultDTO::getTitle)
                .thenComparing(SearchResultDTO::getPrice));
        WebsiteParser websiteParser = new WebsiteParser();
        for (ParsableSiteDTO parsableSiteDTO : Websites.list()) {
            result.addAll(websiteParser.processWebsite(parsableSiteDTO, title));
        }
        result.forEach(this::saveResult);
        return result;
    }

    private void saveResult(SearchResultDTO searchResultDTO) {
        BoardGameDetails boardGameDetails = new BoardGameDetails();
        boardGameDetails.setTitle(searchResultDTO.getTitle());
        boardGameDetails.setLink(searchResultDTO.getLink());
        boardGameDetails.setPrice(searchResultDTO.getPrice());

        if (boardGameRepository.findByLink(searchResultDTO.getLink()) == null) {
            boardGameRepository.save(boardGameDetails);
        }

    }
}
