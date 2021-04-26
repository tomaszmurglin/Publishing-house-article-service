package com.murglin.consulting.publishinghousereviewservice.model.article;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.concurrent.ThreadSafe;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ThreadSafe // value objects should be immutable
public final class Content {

    private final String content;

    //TODO test it
    public static Content create(final String content) {
        if (StringUtils.isBlank(content)) {
            throw new IllegalArgumentException("Content cannot be blank");
        }
        return new Content(content);
    }
}
