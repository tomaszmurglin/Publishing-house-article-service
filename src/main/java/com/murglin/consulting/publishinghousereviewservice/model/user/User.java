package com.murglin.consulting.publishinghousereviewservice.model.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

//TODO add some db constraints if theres no any performance limitation (constraints uses CPU), cause db is the last resort protection
@RequiredArgsConstructor
@Data
public class User {

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
