package pl.drit.learning;

import lombok.Builder;
import lombok.Getter;
import org.jsoup.nodes.Element;

import java.util.function.Function;

@Builder
@Getter
class ParsableSiteDTO {
    private final String siteName;
    private final String firstSiteURL;
    private final Function<Element, String> nextPageFunction;
    private final String productSelector;
    private final Function<Element,String> titleFunction;
    private final Function<Element,String> priceFunction;
}
