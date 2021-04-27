package com.murglin.consulting.publishinghousereviewservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.concurrent.ThreadSafe;
import java.util.UUID;

//TODO normally on this class there would be some validation constraints annotation e.g: from Jackson
@ThreadSafe
@Getter
@RequiredArgsConstructor
public class PublishingRequest {

    private final UUID userId;
    private final UUID articleId;
}
