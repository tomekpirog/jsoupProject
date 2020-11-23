package pl.drit.learning;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

class WebsiteParser {
    private List<SearchResultDTO> results = new ArrayList<>();
    List<SearchResultDTO> processWebsite(ParsableSiteDTO parsableSiteDTO, String title) throws IOException, ParseException {
        System.out.println("-----------------------------------------------------");
        System.out.println("Witryna: " + parsableSiteDTO.getSiteName());
        return processWebsitePage(parsableSiteDTO, parsableSiteDTO.getFirstSiteURL() + URLEncoder.encode(title, StandardCharsets.UTF_8));
    }

    private List<SearchResultDTO> processWebsitePage(ParsableSiteDTO parsableSiteDTO, String pageURL) throws IOException, ParseException {
        Document document = Jsoup.connect(pageURL).get();
        for (Element product : document.select(parsableSiteDTO.getProductSelector())) {
            String title = parsableSiteDTO.getTitleFunction().apply(product);
            double price = PriceParser.parse(parsableSiteDTO.getPriceFunction().apply(product));
            results.add(new SearchResultDTO(title, price));
            System.out.println(title + " ---------- cena: " + price + " zł");
        }

        String nextPageURL = parsableSiteDTO.getNextPageFunction().apply(document);
        if (!StringUtil.isBlank(nextPageURL)) {
            System.out.println(nextPageURL);
            processWebsitePage(parsableSiteDTO, nextPageURL);
        }
        return results;
    }
}
