package com.murglin.consulting.publishinghousereviewservice.model.article;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Title {

    private final String name;

    public static Title create(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Title name cannot be blank");
        }
        return new Title(name);
    }
}
