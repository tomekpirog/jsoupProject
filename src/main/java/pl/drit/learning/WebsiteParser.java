package pl.drit.learning;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.ParseException;

class WebsiteParser {
    static void processWebsite(ParsableSiteDTO parsableSiteDTO) throws IOException, ParseException {

        System.out.println("Witryna: " + parsableSiteDTO.getSiteName());
        processWebsitePage(parsableSiteDTO, parsableSiteDTO.getFirstSiteURL());
        System.out.println("-----------------------------------------------------");
    }

    private static void processWebsitePage(ParsableSiteDTO parsableSiteDTO, String pageURL) throws IOException, ParseException {
        Document document = Jsoup.connect(pageURL).get();
        for (Element product : document.select(parsableSiteDTO.getProductSelector())) {
            String title = parsableSiteDTO.getTitleFunction().apply(product);
            double price = PriceParser.parse(parsableSiteDTO.getPriceFunction().apply(product));
            System.out.println(title + " ---------- cena: " + price + " zł");
        }

        String nextPageURL = parsableSiteDTO.getNextPageFunction().apply(document);
        if (!StringUtil.isBlank(nextPageURL)) {
            System.out.println(nextPageURL);
            processWebsitePage(parsableSiteDTO, nextPageURL);
        }
    }
}
