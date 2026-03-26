package ru.sibiryaq.repository;

import ru.sibiryaq.model.entity.Match;

import java.util.List;

public interface MatchRepository {
    void save(Match match);
    List<Match> findAll(int page, int size);
    List<Match> findByPlayerName(String name, int page, int size);
}
