package ru.sibiryaq.service;

import ru.sibiryaq.model.OngoingMatch;

import java.util.UUID;

public interface OngoingMatchesService {
    OngoingMatch get(UUID id);
    void save(OngoingMatch match);
    void remove(UUID id);
}
