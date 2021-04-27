package com.murglin.consulting.publishinghousereviewservice.model.user;

import lombok.*;

import java.util.UUID;

//TODO add some db constraints if theres no any performance limitation (constraints uses CPU), cause db is the last resort protection
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class User {

    @Getter
    private final UUID id = UUID.randomUUID();

    private final UserRole role;

    //TODO test it
    public boolean isJournalist() {
        return role == UserRole.JOURNALIST;
    }

    //TODO test it
    public boolean isCopyWriter() {
        return role == UserRole.COPYWRITER;
    }
}
