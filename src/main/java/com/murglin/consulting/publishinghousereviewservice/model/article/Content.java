package com.murglin.consulting.publishinghousereviewservice.model.article;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Content {

    private final String content;

    //TODO test it
    public static Content create(final String content) {
        if (content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be blank");
        }
        return new Content(content);
    }
}
