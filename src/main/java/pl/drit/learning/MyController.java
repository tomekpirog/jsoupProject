package pl.drit.learning;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @GetMapping(value = "/boardgame/{title}")
    public Response getBoardgameByName(@PathVariable("title") String title) throws IOException, ParseException {
        List<SearchResultDTO> result = new ArrayList<>();
        WebsiteParser websiteParser = new WebsiteParser();
        for (ParsableSiteDTO parsableSiteDTO : Websites.list()) {
            result.addAll(websiteParser.processWebsite(parsableSiteDTO, title));
        }
        //ObjectMapper mapper = new ObjectMapper();
        String jsonString = new Gson().toJson(result);
        //Object json = mapper.readValue(jsonString, Object.class);
        //String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        return Response.ok(jsonString).build();
    }
}
