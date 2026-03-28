package ru.sibiryaq.controller;

import ru.sibiryaq.exception.InvalidRequestException;
import ru.sibiryaq.exception.MatchNotFoundException;
import ru.sibiryaq.model.OngoingMatch;
import ru.sibiryaq.model.entity.Player;
import ru.sibiryaq.repository.impl.MatchRepositoryImpl;
import ru.sibiryaq.service.FinishedMatchesPersistenceService;
import ru.sibiryaq.service.MatchScoreCalculationService;
import ru.sibiryaq.service.OngoingMatchesService;
import ru.sibiryaq.service.impl.MatchScoreCalculationServiceImpl;
import ru.sibiryaq.service.impl.OngoingMatchesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreController extends HttpServlet {

    private OngoingMatchesService ongoingService;
    private MatchScoreCalculationService scoreService;
    private FinishedMatchesPersistenceService finishedService;

    @Override
    public void init() {
        ongoingService = new OngoingMatchesServiceImpl();
        scoreService = new MatchScoreCalculationServiceImpl();
        finishedService = new FinishedMatchesPersistenceService(new MatchRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        OngoingMatch match = getMatch(req);
        req.setAttribute("match", match);
        req.getRequestDispatcher("/WEB-INF/views/match-score.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        OngoingMatch match = getMatch(req);

        int player;
        try {
            player = Integer.parseInt(req.getParameter("player"));
        } catch (Exception e) {
            throw new InvalidRequestException("Invalid player");
        }

        if (player != 1 && player != 2) {
            throw new InvalidRequestException("Player must be 1 or 2");
        }

        synchronized (match) {

            scoreService.pointWonBy(match, player);

            if (scoreService.isMatchFinished(match)) {
                Player winner = match.getScore().getPlayer1Sets() == 2
                        ? match.getPlayer1()
                        : match.getPlayer2();

                finishedService.saveFinishedMatch(match, winner);
                ongoingService.remove(match.getId());

                resp.sendRedirect(req.getContextPath() + "/matches");
                return;
            }
        }

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + match.getId());
    }

    private OngoingMatch getMatch(HttpServletRequest req) {
        String uuidStr = req.getParameter("uuid");
        if (uuidStr == null) {
            throw new InvalidRequestException("UUID is required");
        }

        UUID id;
        try {
            id = UUID.fromString(uuidStr);
        } catch (Exception e) {
            throw new InvalidRequestException("Invalid UUID");
        }

        OngoingMatch match = ongoingService.get(id);
        if (match == null) {
            throw new MatchNotFoundException("Match not found");
        }

        return match;
    }
}
