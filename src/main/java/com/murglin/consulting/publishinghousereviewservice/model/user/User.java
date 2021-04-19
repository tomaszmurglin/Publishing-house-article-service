package com.murglin.consulting.publishinghousereviewservice.model.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

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
