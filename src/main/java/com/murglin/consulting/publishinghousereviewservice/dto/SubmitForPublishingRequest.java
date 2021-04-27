package com.murglin.consulting.publishinghousereviewservice.dto;

import com.google.common.collect.ImmutableSet;
import com.murglin.consulting.publishinghousereviewservice.model.article.Topic;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.concurrent.ThreadSafe;
import java.util.UUID;

@ThreadSafe
@RequiredArgsConstructor
@Getter
//TODO normally on this class there would be some validation constraints annotation e.g: from Jackson
public final class SubmitForPublishingRequest {

    private final String articleName;
    private final String articleContent;
    private final Topic topic;
    private final ImmutableSet<UUID> copyWriters;
    private final UUID userId;
}
