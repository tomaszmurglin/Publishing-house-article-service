package com.murglin.consulting.publishinghousereviewservice.model.review;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.annotation.concurrent.ThreadSafe;
import java.util.HashSet;
import java.util.Set;

@ThreadSafe
@Getter
@EqualsAndHashCode
public final class SuggestedChanges {

    private final Set<SuggestedChange> suggestedChanges = new HashSet<>();

    public boolean suggest(final SuggestedChange suggestedChange) {
        return this.suggestedChanges.add(suggestedChange);
    }
}
