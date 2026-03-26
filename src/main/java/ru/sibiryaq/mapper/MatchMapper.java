package ru.sibiryaq.mapper;

import ru.sibiryaq.dto.MatchViewDTO;
import ru.sibiryaq.model.entity.Match;

public class MatchMapper {
    public static MatchViewDTO toDto(Match match) {
        MatchViewDTO dto = new MatchViewDTO();
        dto.setPlayer1Name(match.getPlayer1().getName());
        dto.setPlayer2Name(match.getPlayer2().getName());
        dto.setWinnerName(match.getWinner().getName());
        return dto;
    }
}
