package com.murglin.consulting.publishinghousereviewservice.model.user;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class User {

    private final UUID id;

    private final UserRole role;

    public boolean isJournalist() {
        return role == UserRole.JOURNALIST;
    }

    public boolean isCopyWriter() {
        return role == UserRole.COPYWRITER;
    }
}
