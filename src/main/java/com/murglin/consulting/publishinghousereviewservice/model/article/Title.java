package com.murglin.consulting.publishinghousereviewservice.model.article;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Title {

    private final String name;

    //TODO test it
    public static Title create(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Title name cannot be blank");
        }
        return new Title(name);
    }
}
