package com.murglin.consulting.publishinghousereviewservice.model.article;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Content {

    private final String content;

    public static Content create(String content) {
        if (!content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be blank");
        }
        return new Content(content);
    }
}
