package com.murglin.consulting.publishinghousereviewservice.model.article;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Topic {

    private final UUID id = UUID.randomUUID();

    private final String name;

    //TODO test it
    public static Topic create(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Topic name cannot be blank");
        }
        return new Topic(name);
    }
}
