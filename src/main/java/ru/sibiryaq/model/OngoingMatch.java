package ru.sibiryaq.model;

import lombok.Getter;
import lombok.Setter;
import ru.sibiryaq.model.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class OngoingMatch {
    private UUID id;
    private Player player1;
    private Player player2;
    private Score score;
}