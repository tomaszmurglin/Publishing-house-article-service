package com.murglin.consulting.publishinghousereviewservice.model.article;

import lombok.Data;

import java.util.UUID;

@Data
public class Topic {

    private final UUID id;

    private final String name;
}
