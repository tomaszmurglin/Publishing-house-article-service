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

    public Article save(Article article) {
        //TODO publish events from article aggregate here
        return inMemoryDb.put(article.getId(), article);
    }

    public Optional<Article> findById(UUID id) {
        return Optional.ofNullable(inMemoryDb.get(id));
    }
}
