package ru.sibiryaq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sibiryaq.model.OngoingMatch;
import ru.sibiryaq.model.Point;
import ru.sibiryaq.model.Score;
import ru.sibiryaq.service.MatchScoreCalculationService;
import ru.sibiryaq.service.impl.MatchScoreCalculationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

public class MatchScoreCalculationServiceTest {

    private MatchScoreCalculationService service;
    private OngoingMatch match;
    private Score score;

    @BeforeEach
    void setUp() {
        service = new MatchScoreCalculationServiceImpl();
        match = new OngoingMatch();
        score = new Score();
        match.setScore(score);
    }

    @Test
    void shouldGoToAdvantageOnDeuce() {
        score.setPlayer1Points(Point.FORTY);
        score.setPlayer2Points(Point.FORTY);

        service.pointWonBy(match, 1);

        assertEquals(Point.ADVANTAGE, score.getPlayer1Points());
    }

    @Test
    void shouldReturnToDeuceFromAdvantage() {
        score.setPlayer1Points(Point.ADVANTAGE);
        score.setPlayer2Points(Point.FORTY);

        service.pointWonBy(match, 2);

        assertEquals(Point.FORTY, score.getPlayer1Points());
        assertEquals(Point.FORTY, score.getPlayer2Points());
    }

    @Test
    void shouldWinGameFromFortyZero() {
        score.setPlayer1Points(Point.FORTY);
        score.setPlayer2Points(Point.LOVE);

        service.pointWonBy(match, 1);

        assertEquals(1, score.getPlayer1Games());
        assertEquals(Point.LOVE, score.getPlayer1Points());
    }

    @Test
    void shouldWinSet() {
        score.setPlayer1Games(5);
        score.setPlayer2Games(3);

        score.setPlayer1Points(Point.FORTY);
        score.setPlayer2Points(Point.LOVE);

        service.pointWonBy(match, 1);

        assertEquals(1, score.getPlayer1Sets());
        assertEquals(0, score.getPlayer1Games());
    }

    @Test
    void shouldFinishMatch() {
        score.setPlayer1Sets(1);
        score.setPlayer1Games(5);
        score.setPlayer2Games(3);
        score.setPlayer1Points(Point.FORTY);

        service.pointWonBy(match, 1);

        assertTrue(service.isMatchFinished(match));
    }

    @Test
    void shouldWinGameFromAdvantage() {
        score.setPlayer1Points(Point.ADVANTAGE);
        score.setPlayer2Points(Point.FORTY);

        service.pointWonBy(match, 1);

        assertEquals(1, score.getPlayer1Games());
    }

    @Test
    void shouldNotWinSetAt6_5() {
        score.setPlayer1Games(5);
        score.setPlayer2Games(5);

        score.setPlayer1Points(Point.FORTY);
        score.setPlayer2Points(Point.LOVE);

        service.pointWonBy(match, 1);

        assertEquals(6, score.getPlayer1Games());
        assertEquals(0, score.getPlayer1Sets());
    }

    @Test
    void shouldIncreasePointsNormally() {
        score.setPlayer1Points(Point.LOVE);

        service.pointWonBy(match, 1);

        assertEquals(Point.FIFTEEN, score.getPlayer1Points());
    }
}
