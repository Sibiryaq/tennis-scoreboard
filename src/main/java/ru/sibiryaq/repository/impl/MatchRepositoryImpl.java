package ru.sibiryaq.repository.impl;

import ru.sibiryaq.model.entity.Match;
import ru.sibiryaq.repository.MatchRepository;
import ru.sibiryaq.util.TransactionalUtil;

import java.util.List;

public class MatchRepositoryImpl implements MatchRepository {
    @Override
    public void save(Match match) {
        TransactionalUtil.executeVoid(session -> session.save(match));
    }

    @Override
    public List<Match> findAll(int page, int size) {
        int safePage = Math.max(page, 1);
        return TransactionalUtil.execute(session ->
                session.createQuery("FROM Match m ORDER BY m.id DESC", Match.class)
                        .setFirstResult((safePage - 1) * size)
                        .setMaxResults(size)
                        .list()
        );
    }

    @Override
    public List<Match> findByPlayerName(String name, int page, int size) {
        int safePage = Math.max(page, 1);

        return TransactionalUtil.execute(session ->
                session.createQuery(
                                "FROM Match m WHERE LOWER(m.player1.name) = LOWER(:name) " +
                                        "OR LOWER(m.player2.name) = LOWER(:name) ORDER BY m.id DESC",
                                Match.class)
                        .setParameter("name", name)
                        .setFirstResult((safePage - 1) * size)
                        .setMaxResults(size)
                        .list()
        );
    }
}
