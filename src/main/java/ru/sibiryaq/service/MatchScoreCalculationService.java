package ru.sibiryaq.service;

import ru.sibiryaq.model.OngoingMatch;

public interface MatchScoreCalculationService {
    void pointWonBy(OngoingMatch match, int playerNumber);
    boolean isMatchFinished(OngoingMatch match);
}
