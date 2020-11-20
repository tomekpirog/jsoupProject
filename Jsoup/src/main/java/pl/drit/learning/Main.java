package pl.drit.learning;

import java.io.IOException;
import java.text.ParseException;


public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        for (ParsableSiteDTO parsableSiteDTO : Websites.list()) {
            WebsiteParser.processWebsite(parsableSiteDTO);
        }
    }
}


