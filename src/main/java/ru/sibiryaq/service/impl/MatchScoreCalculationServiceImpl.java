package ru.sibiryaq.service.impl;

import ru.sibiryaq.model.OngoingMatch;
import ru.sibiryaq.model.Point;
import ru.sibiryaq.model.Score;
import ru.sibiryaq.service.MatchScoreCalculationService;

public class MatchScoreCalculationServiceImpl implements MatchScoreCalculationService {

    @Override
    public void pointWonBy(OngoingMatch match, int playerNumber) {
        Score score = match.getScore();

        if (playerNumber == 1) {
            processPointWin(score, true);
        } else if (playerNumber == 2) {
            processPointWin(score, false);
        } else {
            throw new IllegalArgumentException("Player must be 1 or 2");
        }

        checkSetWin(score);
    }

    @Override
    public boolean isMatchFinished(OngoingMatch match) {
        return match.getScore().getPlayer1Sets() == 2 || match.getScore().getPlayer2Sets() == 2;
    }

    private void processPointWin(Score score, boolean isPlayerOne) {
        if (score.isTieBreak()) {
            processTieBreak(score, isPlayerOne);
            return;
        }
        Point current = isPlayerOne ? score.getPlayer1Points() : score.getPlayer2Points();
        Point opponent = isPlayerOne ? score.getPlayer2Points() : score.getPlayer1Points();

        if (opponentHasAdvantage(opponent)) {
            resetToDeuce(score);
            return;
        }

        if (currentHasAdvantage(current)) {
            winGame(score, isPlayerOne);
            return;
        }

        if (isGameWin(current, opponent)) {
            winGame(score, isPlayerOne);
            return;
        }

        if (isDeuce(current, opponent)) {
            setPoint(score, isPlayerOne, Point.ADVANTAGE);
            return;
        }

        setPoint(score, isPlayerOne, current.next());
    }

    private boolean opponentHasAdvantage(Point opponent) {
        return opponent == Point.ADVANTAGE;
    }

    private boolean currentHasAdvantage(Point current) {
        return current == Point.ADVANTAGE;
    }

    private boolean isGameWin(Point current, Point opponent) {
        return current == Point.FORTY && opponent != Point.FORTY;
    }

    private boolean isDeuce(Point current, Point opponent) {
        return current == Point.FORTY && opponent == Point.FORTY;
    }

    private void winGame(Score score, boolean isPlayerOne) {
        if (isPlayerOne) {
            score.setPlayer1Games(score.getPlayer1Games() + 1);
        } else {
            score.setPlayer2Games(score.getPlayer2Games() + 1);
        }
        resetPoints(score);
    }

    private void resetPoints(Score score) {
        score.setPlayer1Points(Point.LOVE);
        score.setPlayer2Points(Point.LOVE);
    }

    private void resetToDeuce(Score score) {
        score.setPlayer1Points(Point.FORTY);
        score.setPlayer2Points(Point.FORTY);
    }

    private void setPoint(Score score, boolean isPlayerOne, Point value) {
        if (isPlayerOne) {
            score.setPlayer1Points(value);
        } else {
            score.setPlayer2Points(value);
        }
    }

    private void checkSetWin(Score score) {
        if (score.getPlayer1Games() == 6 && score.getPlayer2Games() == 6) {
            score.setTieBreak(true);
            return;
        }
        if (isSetWon(score.getPlayer1Games(), score.getPlayer2Games())) {
            score.setPlayer1Sets(score.getPlayer1Sets() + 1);
            resetGames(score);
        } else if (isSetWon(score.getPlayer2Games(), score.getPlayer1Games())) {
            score.setPlayer2Sets(score.getPlayer2Sets() + 1);
            resetGames(score);
        }
    }

    private boolean isSetWon(int playerGames, int opponentGames) {
        return playerGames >= 6 && (playerGames - opponentGames) >= 2;
    }

    private void resetGames(Score score) {
        score.setPlayer1Games(0);
        score.setPlayer2Games(0);
    }

    private void processTieBreak(Score score, boolean isPlayerOne) {
        if (isPlayerOne) {
            score.setPlayer1TiePoints(score.getPlayer1TiePoints() + 1);
        } else {
            score.setPlayer2TiePoints(score.getPlayer2TiePoints() + 1);
        }

        int p1 = score.getPlayer1TiePoints();
        int p2 = score.getPlayer2TiePoints();

        if ((p1 >= 7 || p2 >= 7) && Math.abs(p1 - p2) >= 2) {
            if (p1 > p2) {
                score.setPlayer1Sets(score.getPlayer1Sets() + 1);
            } else {
                score.setPlayer2Sets(score.getPlayer2Sets() + 1);
            }

            score.setTieBreak(false);
            score.setPlayer1TiePoints(0);
            score.setPlayer2TiePoints(0);
            resetGames(score);
        }
    }
}
