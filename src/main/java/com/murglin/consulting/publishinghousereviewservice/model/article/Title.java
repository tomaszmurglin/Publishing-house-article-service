package com.murglin.consulting.publishinghousereviewservice.model.article;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.concurrent.ThreadSafe;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ThreadSafe // value objects should be immutable
@EqualsAndHashCode
@ToString
public final class Title {

    private final String name;

    //TODO test it
    public static Title create(final String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Title name cannot be blank");
        }
        return new Title(name);
    }
}
