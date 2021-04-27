package com.murglin.consulting.publishinghousereviewservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.concurrent.ThreadSafe;
import java.util.UUID;

@ThreadSafe
//TODO normally on this class there would be some validation constraints annotation e.g: from Jackson
@RequiredArgsConstructor
@Getter
public final class SuggestChangesRequest {

    private final UUID userId;
    private final String remarks;
    private final UUID articleId;
}
