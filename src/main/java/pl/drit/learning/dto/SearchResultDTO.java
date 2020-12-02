package pl.drit.learning.dto;

import lombok.Getter;

@Getter
public class SearchResultDTO {
    private final String title;
    private final double price;
    private final String link;

    public SearchResultDTO(String title, double price, String link) {
        this.title = title;
        this.price = price;
        this.link = link;
    }
}
