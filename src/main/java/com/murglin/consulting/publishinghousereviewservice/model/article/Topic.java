package com.murglin.consulting.publishinghousereviewservice.model.article;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

//TODO add some db constraints if theres no any performance limitation (constraints uses CPU), cause db is the last resort protection
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Topic {

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
