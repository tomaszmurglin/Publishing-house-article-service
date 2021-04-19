package com.murglin.consulting.publishinghousereviewservice.model.article;

import com.murglin.consulting.publishinghousereviewservice.model.review.Review;
import com.murglin.consulting.publishinghousereviewservice.model.review.SuggestedChanges;
import com.murglin.consulting.publishinghousereviewservice.model.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Article {

    private final UUID id = UUID.randomUUID();

    private ArticleStatus status;

    private Title title;

    private Content content;

    private Topic topic;

    private Review review;

    private User author;

    public void suggestChanges(final SuggestedChanges suggestedChanges) {
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

    public void publish(final User publisher) {
        if (!author.equals(publisher)) { //TODO only author or any journalist can publish article ?
            throw new IllegalArgumentException("Only author can publish the article");
        }
        if (status != ArticleStatus.IN_REVIEW) {
            throw new IllegalStateException("Only in - review article can be published"); //TODO can publish draft without review ?
        }
        status = ArticleStatus.PUBLISHED;
        review.complete();
        //TODO generate event to publish here
    }

    public static Article create(final Title title, final Content content, final Topic topic,
                                 final Review review, final User author) {
        return new Article(ArticleStatus.DRAFT, title, content, topic, review, author);
    }
}
