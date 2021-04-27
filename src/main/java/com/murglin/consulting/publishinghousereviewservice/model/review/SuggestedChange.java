package com.murglin.consulting.publishinghousereviewservice.model.review;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.concurrent.ThreadSafe;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@ThreadSafe
public final class SuggestedChange {

    @Getter
    private final UUID copyWriterId;

    private final String remarks;

    @Getter
    private final boolean resolved = false;

    //TODO test it
    public static SuggestedChange create(final UUID copyWriterId, final String remarks) {
        if (!ObjectUtils.allNotNull(copyWriterId, remarks)) {
            throw new IllegalArgumentException("All arguments must be non-null");
        }
        if (remarks.isBlank()) {
            throw new IllegalStateException("Suggested changes should have meaningful remarks");
        }
        //TODO generate event to publish here
        return new SuggestedChange(copyWriterId, remarks);
    }
}
