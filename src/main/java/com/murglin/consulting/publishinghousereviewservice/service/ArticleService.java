package com.murglin.consulting.publishinghousereviewservice.service;

import com.murglin.consulting.publishinghousereviewservice.dto.PublishingRequest;
import com.murglin.consulting.publishinghousereviewservice.dto.SubmitForPublishingRequest;
import com.murglin.consulting.publishinghousereviewservice.dto.SuggestChangesRequest;
import com.murglin.consulting.publishinghousereviewservice.exception.NotFoundException;
import com.murglin.consulting.publishinghousereviewservice.exception.UnauthorizedException;
import com.murglin.consulting.publishinghousereviewservice.model.article.Article;
import com.murglin.consulting.publishinghousereviewservice.model.article.Content;
import com.murglin.consulting.publishinghousereviewservice.model.article.Title;
import com.murglin.consulting.publishinghousereviewservice.model.review.Review;
import com.murglin.consulting.publishinghousereviewservice.model.review.SuggestedChange;
import com.murglin.consulting.publishinghousereviewservice.repository.ArticleRepository;
import com.murglin.consulting.publishinghousereviewservice.repository.TopicRepository;
import com.murglin.consulting.publishinghousereviewservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    private final TopicRepository topicRepository;

    //TODO test it
    @Transactional //of course it wont work without transactional data store like hashmap - its just an example
    public Article submitForPublishing(final SubmitForPublishingRequest submitForPublishingRequest) {
        log.info("SubmitForPublishingRequest has been received '{}'", submitForPublishingRequest);
        //TODO validation of input data in layer above before (eg. json schema) or after deserialization (eg. jackson)
        final var author = userRepository.findById(submitForPublishingRequest.getUserId()).orElseThrow(() -> new NotFoundException("User has not been found"));
        if (author.isJournalist()) { //TODO authorization could be moved into controllers layer of the app using e.g Spring security
            throw new UnauthorizedException("Only Journalist can submit article for publishing");
        }
        if (!topicRepository.allExists(Set.of(submitForPublishingRequest.getTopic()))) {
            throw new NotFoundException("Some of provided topics have not been found");
        }
        userRepository.findByIdIn(submitForPublishingRequest.getCopyWriters()).forEach(copyWriter -> {
            var user = copyWriter.orElseThrow(() -> new NotFoundException("User has not been found"));
            if (!user.isCopyWriter()) {
                throw new IllegalArgumentException("Only copywriters could be assigned to review article");
            }
        });
        final var title = Title.create(submitForPublishingRequest.getArticleName());
        final var content = Content.create(submitForPublishingRequest.getArticleContent());
        final var review = Review.create(submitForPublishingRequest.getCopyWriters());
        final var newArticle = Article.create(title, content, submitForPublishingRequest.getTopic(), review, author);
        articleRepository.save(newArticle);
        log.info("An article '{}' has been successfully submitted for publishing", newArticle);
        //TODO publish some metrics with evaluated time to the monitoring solution eg: New Relic
        return newArticle;
    }

    //TODO test it
    @Transactional //of course it wont work without transactional data store like hashmap - its just an example
    public Article suggestChanges(final SuggestChangesRequest suggestChangesRequest) {
        log.info("SuggestChangesRequest has been received '{}'", suggestChangesRequest);
        //TODO validation of input data in layer above before (eg. json schema) or after deserialization (eg. jackson)
        final var copyWriter = userRepository.findById(suggestChangesRequest.getUserId()).orElseThrow(() -> new NotFoundException("User has not been found"));
        if (copyWriter.isCopyWriter()) { //TODO authorization could be moved into controllers layer of the app using e.g Spring security
            throw new UnauthorizedException("Only Copywriter can suggest changes to an article");
        }
        final var suggestedChanges = SuggestedChange.create(suggestChangesRequest.getUserId(), suggestChangesRequest.getRemarks());
        final var article = articleRepository.findById(suggestChangesRequest.getArticleId()).orElseThrow(() -> new NotFoundException("Article has not been found"));
        article.suggestChanges(suggestedChanges);
        articleRepository.save(article);
        log.info("Changes '{}' have been successfully suggested to an article '{}'", suggestedChanges, article);
        //TODO publish some metrics with evaluated time to the monitoring solution eg: New Relic
        return article;
    }

    //TODO test it
    @Transactional //of course it wont work without transactional data store like hashmap - its just an example
    public Article publish(final PublishingRequest publishingRequest) {
        log.info("PublishingRequest has been received '{}'", publishingRequest);
        final var article = articleRepository.findById(publishingRequest.getArticleId()).orElseThrow(() -> new NotFoundException("Article has not been found"));
        final var user = userRepository.findById(publishingRequest.getUserId()).orElseThrow(() -> new NotFoundException("User has not been found"));
        article.publish(user);
        articleRepository.save(article);
        log.info("An article '{}' has been successfully published", article);
        //TODO publish some metrics with evaluated time to the monitoring solution eg: New Relic
        return article;
    }
}
