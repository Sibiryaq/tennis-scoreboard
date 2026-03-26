package ru.sibiryaq.service;

import ru.sibiryaq.model.entity.Match;
import ru.sibiryaq.model.OngoingMatch;
import ru.sibiryaq.model.entity.Player;
import ru.sibiryaq.repository.MatchRepository;

public class FinishedMatchesPersistenceService {

    private final MatchRepository matchRepository;

    public FinishedMatchesPersistenceService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public void saveFinishedMatch(OngoingMatch ongoingMatch, Player winner) {
        Match match = new Match();
        match.setPlayer1(ongoingMatch.getPlayer1());
        match.setPlayer2(ongoingMatch.getPlayer2());
        match.setWinner(winner);

        matchRepository.save(match);
    }
}
