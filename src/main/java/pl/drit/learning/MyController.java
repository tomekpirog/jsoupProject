package pl.drit.learning;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MyController {

    @GetMapping(value = "/")
    public String home() {
        return "home page";
    }


    @Produces(MediaType.APPLICATION_JSON)
    @CrossOrigin(origins = "http://127.0.0.1:50856")
    @GetMapping(value = "/boardgame/{title}")
    public List<SearchResultDTO> getBoardgameByName(@PathVariable("title") String title) throws IOException, ParseException {
        List<SearchResultDTO> result = new ArrayList<>();
        WebsiteParser websiteParser = new WebsiteParser();
        for (ParsableSiteDTO parsableSiteDTO : Websites.list()) {
            result.addAll(websiteParser.processWebsite(parsableSiteDTO, title));
        }
        return result;
    }
}
