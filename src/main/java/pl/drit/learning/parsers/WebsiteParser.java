package pl.drit.learning.parsers;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.drit.learning.dto.ParsableSiteDTO;
import pl.drit.learning.dto.SearchResultDTO;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class WebsiteParser {
    private List<SearchResultDTO> results = new ArrayList<>();
    public List<SearchResultDTO> processWebsite(ParsableSiteDTO parsableSiteDTO, String title) throws IOException, ParseException {
        System.out.println("-----------------------------------------------------");
        System.out.println("Witryna: " + parsableSiteDTO.getSiteName());
        return processWebsitePage(parsableSiteDTO, parsableSiteDTO.getFirstSiteURL() + URLEncoder.encode(title, StandardCharsets.UTF_8));
    }

    private List<SearchResultDTO> processWebsitePage(ParsableSiteDTO parsableSiteDTO, String pageURL) {
        try {
            Document document = Jsoup.connect(pageURL).timeout(10 * 1000).get();
            for (Element product : document.select(parsableSiteDTO.getProductSelector())) {
                String title = parsableSiteDTO.getTitleFunction().apply(product);
                String link = parsableSiteDTO.getLinkFunction().apply(product);
                double price = PriceParser.parse(parsableSiteDTO.getPriceFunction().apply(product));
                results.add(new SearchResultDTO(title, price, link));
                System.out.println(title + " ---------- cena: " + price + " zł // LINK: " + link);
            }
            String nextPageURL = parsableSiteDTO.getNextPageFunction().apply(document);
            if (!StringUtil.isBlank(nextPageURL)) {
                System.out.println(nextPageURL);
                processWebsitePage(parsableSiteDTO, nextPageURL);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return results;
    }
}
