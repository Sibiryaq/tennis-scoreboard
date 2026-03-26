package ru.sibiryaq.controller;

import ru.sibiryaq.dto.MatchViewDTO;
import ru.sibiryaq.exception.InvalidRequestException;
import ru.sibiryaq.model.entity.Match;
import ru.sibiryaq.repository.MatchRepository;
import ru.sibiryaq.repository.impl.MatchRepositoryImpl;
import ru.sibiryaq.util.MatchMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class MatchesController extends HttpServlet {

    private MatchRepository matchRepository;

    @Override
    public void init() {
        matchRepository = new MatchRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int page = 1;
        String pageParam = req.getParameter("page");

        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) throw new InvalidRequestException("Page must be >= 1");
            } catch (NumberFormatException e) {
                throw new InvalidRequestException("Invalid page");
            }
        }

        String filter = req.getParameter("filter_by_player_name");

        List<Match> matches = (filter != null && !filter.isBlank())
                ? matchRepository.findByPlayerName(filter, page, 10)
                : matchRepository.findAll(page, 10);

        List<MatchViewDTO> dtoList = matches.stream()
                .map(MatchMapper::toDto)
                .toList();

        req.setAttribute("matches", dtoList);
        req.getRequestDispatcher("/WEB-INF/views/matches.jsp")
                .forward(req, resp);
    }
}