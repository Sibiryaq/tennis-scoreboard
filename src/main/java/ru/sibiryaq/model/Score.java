package ru.sibiryaq.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Score {
    private Point player1Points = Point.LOVE;
    private Point player2Points = Point.LOVE;

    private int player1Games = 0;
    private int player2Games = 0;

    private int player1Sets = 0;
    private int player2Sets = 0;
}