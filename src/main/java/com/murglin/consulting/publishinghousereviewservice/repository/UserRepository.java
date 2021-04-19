package com.murglin.consulting.publishinghousereviewservice.repository;

import com.murglin.consulting.publishinghousereviewservice.model.user.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private final Map<UUID, User> inMemoryDb = new ConcurrentHashMap<>();

    //TODO test it
    public Optional<User> findById(final UUID id) {
        return Optional.ofNullable(inMemoryDb.get(id));
    }

    //TODO test it
    public Set<Optional<User>> findByIdIn(final Set<UUID> ids) {
        return ids.stream()
                .map(id -> Optional.ofNullable(inMemoryDb.get(id)))
                .collect(Collectors.toUnmodifiableSet());
    }
}
