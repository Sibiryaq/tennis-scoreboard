package ru.sibiryaq.controller;

import ru.sibiryaq.exception.InvalidRequestException;
import ru.sibiryaq.model.OngoingMatch;
import ru.sibiryaq.model.entity.Player;
import ru.sibiryaq.model.Score;
import ru.sibiryaq.repository.PlayerRepository;
import ru.sibiryaq.repository.impl.PlayerRepositoryImpl;
import ru.sibiryaq.service.OngoingMatchesService;
import ru.sibiryaq.service.impl.OngoingMatchesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchController extends HttpServlet {

    private PlayerRepository playerRepo;
    private OngoingMatchesService ongoingService;

    @Override
    public void init() {
        playerRepo = new PlayerRepositoryImpl();
        ongoingService = new OngoingMatchesServiceImpl();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/new-match.jsp")
                .forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String p1Name = req.getParameter("player1");
        String p2Name = req.getParameter("player2");

        if (p1Name == null || p2Name == null || p1Name.isBlank() || p2Name.isBlank()) {
            req.setAttribute("error", "Имена игроков не могут быть пустыми");

            req.setAttribute("player1", p1Name);
            req.setAttribute("player2", p2Name);

            req.getRequestDispatcher("/WEB-INF/views/new-match.jsp").forward(req, resp);
            return;
        }

        p1Name = p1Name.trim();
        p2Name = p2Name.trim();

        if (p1Name.equals(p2Name)) {
            req.setAttribute("error", "Игроки должны быть разными");
            req.setAttribute("player1", p1Name);
            req.setAttribute("player2", p2Name);

            req.getRequestDispatcher("/WEB-INF/views/new-match.jsp")
                    .forward(req, resp);
            return;
        }

        Player p1 = playerRepo.findOrCreate(p1Name);
        Player p2 = playerRepo.findOrCreate(p2Name);

        OngoingMatch match = new OngoingMatch();
        match.setId(UUID.randomUUID());
        match.setPlayer1(p1);
        match.setPlayer2(p2);
        match.setScore(new Score());

        ongoingService.save(match);

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + match.getId());
    }
}
