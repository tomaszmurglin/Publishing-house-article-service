package com.murglin.consulting.publishinghousereviewservice.model.article;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

//TODO add some db constraints if theres no any performance limitation (constraints uses CPU), cause db is the last resort protection
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Topic {

    @Getter
    private final UUID id = UUID.randomUUID();

    private final String name;

    //TODO test it
    public static Topic create(final String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Topic name cannot be blank");
        }
        return new Topic(name);
    }
}
