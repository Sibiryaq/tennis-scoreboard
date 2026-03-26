package ru.sibiryaq.repository;

import ru.sibiryaq.model.entity.Player;

public interface PlayerRepository {
    Player findByName(String name);
    void save(Player player);
    Player findOrCreate(String name);
}