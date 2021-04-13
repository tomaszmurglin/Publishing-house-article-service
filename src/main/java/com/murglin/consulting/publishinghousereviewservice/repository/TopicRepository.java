package com.murglin.consulting.publishinghousereviewservice.repository;

import com.murglin.consulting.publishinghousereviewservice.model.article.Topic;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TopicRepository {

    private final Map<UUID, Topic> inMemoryDb = new ConcurrentHashMap<>();

    public boolean allExists(Set<Topic> topics) {
        return topics
                .stream()
                .allMatch(topic -> inMemoryDb.containsKey(topic.getId()));
    }
}
