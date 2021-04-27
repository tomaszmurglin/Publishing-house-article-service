package com.murglin.consulting.publishinghousereviewservice.model.article;

import com.murglin.consulting.publishinghousereviewservice.model.review.Review;
import com.murglin.consulting.publishinghousereviewservice.model.review.SuggestedChange;
import com.murglin.consulting.publishinghousereviewservice.model.user.User;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.UUID;

//TODO add some db constraints if theres no any performance limitation (constraints uses CPU), cause db is the last resort protection
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Article {

    @Getter
    private final UUID id = UUID.randomUUID();

    private ArticleStatus status;

    private final Title title;

    private final Content content;

    private final Topic topic;

    private final Review review;

    private final User author;

    public void suggestChanges(final SuggestedChange suggestedChange) {
        if (author.getId().equals(suggestedChange.getCopyWriterId())) {
            throw new IllegalStateException("Author cannot suggest remarks to its own article"); //TODO question - can or not ?
        }
        if (status == ArticleStatus.PUBLISHED) {
            throw new IllegalStateException("Cannot suggest changes to already published article");
        }
        status = ArticleStatus.IN_REVIEW;
        review.suggestChanges(suggestedChange);
        checkInvariants();
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
        checkInvariants();
        //TODO generate event to publish here
    }

    public static Article create(final Title title, final Content content, final Topic topic,
                                 final Review review, final User author) {
        if (!ObjectUtils.allNotNull(title, content, topic, review, author)) {
            throw new IllegalArgumentException("All arguments must be non-null");
        }
        return new Article(ArticleStatus.DRAFT, title, content, topic, review, author);
    }

    private void checkInvariants() {
        if (title == null) {
            throw new IllegalStateException("Title must be non-null");
        }
        if (content == null) {
            throw new IllegalStateException("Content must be non-null");
        }
        if (topic == null) {
            throw new IllegalStateException("Topic must be non-null");
        }
        if (review == null) {
            throw new IllegalStateException("Review must be non-null");
        }
        if (author == null) {
            throw new IllegalStateException("Author must be non-null");
        }
    }
}
