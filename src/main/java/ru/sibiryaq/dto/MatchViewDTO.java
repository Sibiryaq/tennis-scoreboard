package ru.sibiryaq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchViewDTO {
    private String player1Name;
    private String player2Name;
    private String winnerName;
}