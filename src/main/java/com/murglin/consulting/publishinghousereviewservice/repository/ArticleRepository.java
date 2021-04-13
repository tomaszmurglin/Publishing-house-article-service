package com.murglin.consulting.publishinghousereviewservice.repository;

import com.murglin.consulting.publishinghousereviewservice.model.article.Article;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ArticleRepository {

    private final Map<UUID, Article> inMemoryDb = new ConcurrentHashMap<>();

    public Article save(Article article){
        return inMemoryDb.put(article.getId(), article);
    }
}
