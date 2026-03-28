package ru.sibiryaq.controller;

import ru.sibiryaq.dto.MatchViewDTO;
import ru.sibiryaq.exception.InvalidRequestException;
import ru.sibiryaq.model.entity.Match;
import ru.sibiryaq.repository.MatchRepository;
import ru.sibiryaq.repository.impl.MatchRepositoryImpl;
import ru.sibiryaq.mapper.MatchMapper;

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
    private static final int PAGE_SIZE = 5;

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
                if (page < 1) {
                    throw new InvalidRequestException("Page must be >= 1");
                }
            } catch (NumberFormatException e) {
                throw new InvalidRequestException("Invalid page");
            }
        }

        String filter = req.getParameter("filter_by_player_name");

        List<Match> matches = (filter != null && !filter.isBlank())
                ? matchRepository.findByPlayerName(filter.trim(), page, PAGE_SIZE)
                : matchRepository.findAll(page, PAGE_SIZE);

        if (matches.isEmpty() && page > 1) {
            resp.sendRedirect(req.getContextPath() + "/matches?page=1" +
                    (filter != null ? "&filter_by_player_name=" + filter : ""));
            return;
        }

        List<MatchViewDTO> dtoList = matches.stream()
                .map(MatchMapper::toDto)
                .toList();

        req.setAttribute("matches", dtoList);
        req.setAttribute("currentPage", page);
        req.setAttribute("hasNextPage", matches.size() == PAGE_SIZE);
        req.setAttribute("filter", filter);

        req.getRequestDispatcher("/WEB-INF/views/matches.jsp")
                .forward(req, resp);
    }
}