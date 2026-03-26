package ru.sibiryaq.service.impl;

import ru.sibiryaq.model.OngoingMatch;
import ru.sibiryaq.service.OngoingMatchesService;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesServiceImpl implements OngoingMatchesService {

    private static final Map<UUID, OngoingMatch> storage = new ConcurrentHashMap<>();

    @Override
    public OngoingMatch get(UUID id) {
        return storage.get(id);
    }

    @Override
    public void save(OngoingMatch match) {
        storage.put(match.getId(), match);
    }

    @Override
    public void remove(UUID id) {
        storage.remove(id);
    }
}
