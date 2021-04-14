package com.murglin.consulting.publishinghousereviewservice.model.review;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class Review {

    private final UUID id = UUID.randomUUID();

    private final ReviewStatus status = ReviewStatus.IN_PROGRESS;

    private final Set<SuggestedChanges> suggestedChanges = new HashSet<>();

    private final Set<UUID> reviewersIds;

    public void suggestChanges(SuggestedChanges suggestedChanges) {
        if (status == ReviewStatus.COMPLETED) {
            throw new IllegalStateException("Completed review cant be modified");
        }
        if (!reviewersIds.contains(suggestedChanges.getCopyWriterId())) { //TODO question should we assigned not - assigned already reviewer instead ?
            throw new IllegalStateException("Reviewer not assigned to the review");
        }
        this.suggestedChanges.add(suggestedChanges);
    }

    public static Review create(Set<UUID> reviewersIds) {
        if (reviewersIds.isEmpty()) {
            throw new IllegalArgumentException("Review must have reviewers"); //TODO or reviewers can be assigned later on ?
        }
        return new Review(reviewersIds);
    }
}
