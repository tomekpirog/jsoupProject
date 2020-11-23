package pl.drit.learning;

import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
public class MyController {

    @GetMapping(value = "/")
    public String home() {
        return "home page";
    }

    @GetMapping(value = "/boardgame/{title}")
    public List<List<>> getBoardgameByName(@PathVariable("title") String title) throws IOException, ParseException {
        return Websites.list();
    }
}
