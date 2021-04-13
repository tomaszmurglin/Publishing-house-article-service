package com.murglin.consulting.publishinghousereviewservice.model.review;

import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class Review {

    private final UUID id;

    private final UUID articleId;

    private final ReviewStatus status;

    private final Set<SuggestedChanges> suggestedChanges = Set.of();

    private final Set<UUID> reviewersIds;
}
