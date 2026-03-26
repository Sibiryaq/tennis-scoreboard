package ru.sibiryaq.repository.impl;

import org.hibernate.exception.ConstraintViolationException;
import ru.sibiryaq.model.entity.Player;
import ru.sibiryaq.repository.PlayerRepository;
import ru.sibiryaq.util.TransactionalUtil;

public class PlayerRepositoryImpl implements PlayerRepository {

    @Override
    public Player findByName(String name) {
        return TransactionalUtil.executeRead(session ->
                session.createQuery("FROM Player p WHERE p.name = :name", Player.class)
                        .setParameter("name", name)
                        .uniqueResult()
        );
    }

    @Override
    public void save(Player player) {
        TransactionalUtil.executeVoid(session -> session.save(player));
    }

    @Override
    public Player findOrCreate(String name) {
        Player existing = findByName(name);
        if (existing != null) {
            return existing;
        }

        try {
            Player player = new Player();
            player.setName(name);
            save(player);
            return player;
        } catch (ConstraintViolationException e) {
            return findByName(name);
        }
    }
}
