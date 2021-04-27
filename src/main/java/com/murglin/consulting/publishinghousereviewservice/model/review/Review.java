package com.murglin.consulting.publishinghousereviewservice.model.review;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//TODO add some db constraints if theres no any performance limitation (constraints uses CPU), cause db is the last resort protection
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Review {

    private final UUID id = UUID.randomUUID();

    private ReviewStatus status = ReviewStatus.IN_PROGRESS;

    private final Set<SuggestedChanges> suggestedChanges = new HashSet<>();

    private final Set<UUID> reviewersIds;

    //TODO test it
    public void suggestChanges(final SuggestedChanges suggestedChanges) {
        if (status == ReviewStatus.COMPLETED) {
            throw new IllegalStateException("Completed review cant be modified");
        }
        if (!reviewersIds.contains(suggestedChanges.getCopyWriterId())) { //TODO question should we assigned not - assigned already reviewer instead ?
            throw new IllegalStateException("Reviewer not assigned to the review");
        }
        this.suggestedChanges.add(suggestedChanges);
        checkInvariants();
    }

    //TODO test it
    public void complete() {
        if (status == ReviewStatus.COMPLETED) {
            throw new IllegalStateException("Cannot complete already completed review");
        }
        if (!areAllRemarksResolved()) {
            throw new IllegalStateException("Cannot complete review with not resolved remarks");
        }
        status = ReviewStatus.COMPLETED;
        checkInvariants();
        //TODO generate event to publish here
    }

    //TODO test it
    public static Review create(final Set<UUID> reviewersIds) {
        if (CollectionUtils.isEmpty(reviewersIds)) {
            throw new IllegalArgumentException("Review must have reviewers"); //TODO or reviewers can be assigned later on ?
        }
        return new Review(reviewersIds);
    }

    private boolean areAllRemarksResolved() {
        return suggestedChanges.stream().allMatch(SuggestedChanges::isResolved);
    }

    private void checkInvariants() {
        if (CollectionUtils.isEmpty(reviewersIds)) {
            throw new IllegalStateException("Review must have reviewers"); //TODO or reviewers can be assigned later on ?
        }
    }
}
