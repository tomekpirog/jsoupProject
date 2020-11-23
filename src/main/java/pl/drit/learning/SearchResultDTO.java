package pl.drit.learning;

import lombok.Getter;

@Getter
public class SearchResultDTO {
    private final String title;
    private final double price;

    public SearchResultDTO(String title, double price) {
        this.title = title;
        this.price = price;
    }
}
