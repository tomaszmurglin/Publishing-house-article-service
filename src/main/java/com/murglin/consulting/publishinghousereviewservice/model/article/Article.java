package com.murglin.consulting.publishinghousereviewservice.model.article;

import com.murglin.consulting.publishinghousereviewservice.model.review.Review;
import com.murglin.consulting.publishinghousereviewservice.model.review.SuggestedChanges;
import com.murglin.consulting.publishinghousereviewservice.model.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Article {

    private final UUID id = UUID.randomUUID();

    private ArticleStatus status;

    private Title title;

    private Content content;

    private Set<Topic> topics;

    private Review review;

    private User author;

    public void suggestChanges(SuggestedChanges suggestedChanges) {
        if (author.getId().equals(suggestedChanges.getCopyWriterId())) {
            throw new IllegalStateException("Author cannot suggest remarks to its own article"); //TODO question - can or not ?
        }
        if (status == ArticleStatus.PUBLISHED) {
            throw new IllegalStateException("Cannot suggest changes to already published article");
        }
        status = ArticleStatus.IN_REVIEW;
        review.suggestChanges(suggestedChanges);
        //TODO generate event to publish here
    }

    public static Article create(Title title, Content content, Set<Topic> topics, Review review, User author) {
        if (topics.isEmpty()) {
            throw new IllegalArgumentException("Cannot create article without topic");
        }
        return new Article(ArticleStatus.DRAFT, title, content, topics, review, author);
    }
}
