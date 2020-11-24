package pl.drit.learning;

import lombok.Getter;

@Getter
class SearchResultDTO {
    private final String title;
    private final double price;
    private final String link;

    SearchResultDTO(String title, double price, String link) {
        this.title = title;
        this.price = price;
        this.link = link;
    }
}
