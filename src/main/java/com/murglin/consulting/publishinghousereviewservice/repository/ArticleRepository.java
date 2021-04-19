package com.murglin.consulting.publishinghousereviewservice.repository;

import com.murglin.consulting.publishinghousereviewservice.model.article.Article;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ArticleRepository {

    private final Map<UUID, Article> inMemoryDb = new ConcurrentHashMap<>();

    //TODO test it
    public Article save(final Article article) {
        //TODO publish events from article aggregate here
        return inMemoryDb.put(article.getId(), article);
    }

    //TODO test it
    public Optional<Article> findById(final UUID id) {
        return Optional.ofNullable(inMemoryDb.get(id));
    }
}
