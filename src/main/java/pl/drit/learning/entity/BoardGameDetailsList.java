package pl.drit.learning.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class BoardGameDetailsList {

    public List<BoardGameDetails> boardGameDetailsList;

    public BoardGameDetailsList() {
        boardGameDetailsList = new ArrayList<>();
    }

    public BoardGameDetailsList(List<BoardGameDetails> boardGameDetailsList){
        this.boardGameDetailsList = boardGameDetailsList;
    }
}
