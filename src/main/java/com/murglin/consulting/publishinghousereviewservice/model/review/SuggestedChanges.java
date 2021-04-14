package com.murglin.consulting.publishinghousereviewservice.model.review;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class SuggestedChanges {

    private final UUID id = UUID.randomUUID();

    private final UUID copyWriterId;

    private final String remarks;

    private final boolean resolved = false;

    public static SuggestedChanges create(UUID copyWriterId, String remarks) {
        if (remarks.isBlank()) {
            throw new IllegalStateException("Suggested changes should have meaningful remarks");
        }
        //TODO generate event to publish here
        return new SuggestedChanges(copyWriterId, remarks);
    }
}
