package pl.drit.learning;

import java.util.List;

//@Component
class Websites {


    private static final String GRY_PLANSZOWE_URL = "https://gryplanszowe.pl/search.php?text=";
    private static final ParsableSiteDTO GRY_PLANSZOWE_DTO = ParsableSiteDTO.builder()
            .siteName("gryplanszowe.pl")
            .firstSiteURL(GRY_PLANSZOWE_URL)
            .productSelector("#search .product")
            .titleFunction((product) -> product.select(".product__name").attr("title"))
            .priceFunction((product) -> product.select("strong.price").text())
            .nextPageFunction((page)-> page.select(".pagination__button").attr("abs:href"))
            .build();
    private static final String ALE_PLANSZOWKI_URL = "https://aleplanszowki.pl/search?controller=search&orderby=quantity&orderway=desc&search_query=";
    private static final ParsableSiteDTO ALE_PLANSZOWKI_DTO = ParsableSiteDTO.builder()
            .siteName("aleplanszowki.pl")
            .firstSiteURL(ALE_PLANSZOWKI_URL)
            .productSelector("#center_column .product-container")
            .titleFunction((product) -> product.select(".product-name").attr("title"))
            .priceFunction((product) -> product.select(".price").text())
            .nextPageFunction((page)-> page.select("#pagination_next_bottom a").attr("abs:href"))
            .build();
    private static final String TROLLE_URL = "https://3trolle.pl/jolisearch?search_query=";
    private static final ParsableSiteDTO TROLLE_DTO = ParsableSiteDTO.builder()
            .siteName("3TROLLE.pl")
            .firstSiteURL(TROLLE_URL)
            .productSelector(".product-container")
            .titleFunction((product) -> product.select(".product-name").attr("title"))
            .priceFunction((product) -> product.select(".price_container .price").text())
            .nextPageFunction((page)-> page.select("#pagination_next_bottom a").attr("abs:href"))
            .build();

    static  List<ParsableSiteDTO> list() {
        return List.of(ALE_PLANSZOWKI_DTO, GRY_PLANSZOWE_DTO, TROLLE_DTO);
    }
}
