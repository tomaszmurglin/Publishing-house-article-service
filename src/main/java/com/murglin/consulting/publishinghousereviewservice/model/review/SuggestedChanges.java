package com.murglin.consulting.publishinghousereviewservice.model.review;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.util.UUID;

//TODO add some db constraints if theres no any performance limitation (constraints uses CPU), cause db is the last resort protection
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class SuggestedChanges {

    private final UUID id = UUID.randomUUID();

    private final UUID copyWriterId;

    private final String remarks;

    private final boolean resolved = false;

    //TODO test it
    public static SuggestedChanges create(final UUID copyWriterId, final String remarks) {
        if (!ObjectUtils.allNotNull(copyWriterId, remarks)) {
            throw new IllegalArgumentException("All arguments must be non-null");
        }
        if (remarks.isBlank()) {
            throw new IllegalStateException("Suggested changes should have meaningful remarks");
        }
        //TODO generate event to publish here
        return new SuggestedChanges(copyWriterId, remarks);
    }
}
