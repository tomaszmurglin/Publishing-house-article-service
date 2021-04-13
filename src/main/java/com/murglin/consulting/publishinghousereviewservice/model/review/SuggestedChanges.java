package com.murglin.consulting.publishinghousereviewservice.model.review;

import lombok.Data;

import java.util.UUID;

@Data
public class SuggestedChanges {

    private final UUID id;

    private final UUID copyWriterId;

    private final String remarks;

    private final boolean resolved;
}
