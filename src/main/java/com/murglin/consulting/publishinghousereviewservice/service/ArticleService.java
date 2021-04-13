package com.murglin.consulting.publishinghousereviewservice.service;

import com.murglin.consulting.publishinghousereviewservice.exception.NotFoundException;
import com.murglin.consulting.publishinghousereviewservice.exception.UnauthorizedException;
import com.murglin.consulting.publishinghousereviewservice.model.article.*;
import com.murglin.consulting.publishinghousereviewservice.model.review.Review;
import com.murglin.consulting.publishinghousereviewservice.model.review.ReviewStatus;
import com.murglin.consulting.publishinghousereviewservice.model.review.SuggestedChanges;
import com.murglin.consulting.publishinghousereviewservice.repository.ArticleRepository;
import com.murglin.consulting.publishinghousereviewservice.repository.TopicRepository;
import com.murglin.consulting.publishinghousereviewservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    private final TopicRepository topicRepository;

    public Article submitForPublishing(String articleName, String articleContent, Set<Topic> topics, Set<UUID> copyWriters,
                                       UUID publisherId) {
        //TODO validation of input data in layer above before (eg. json schema) or after deserialization (eg. jackson)

        var publisher = userRepository.findById(publisherId).orElseThrow(() -> new NotFoundException("User has not been found"));
        if (publisher.isJournalist()) { //TODO authorization could be moved into controllers layer of the app
            throw new UnauthorizedException("Only Journalist can submit article for publishing");
        }
        if (!topicRepository.allExists(topics)) {
            throw new NotFoundException("Some of provided topics have not exist");
        }
        userRepository.findByIdIn(copyWriters).forEach(copyWriter -> {
            var user = copyWriter.orElseThrow(() -> new NotFoundException("User has not been found"));
            if (!user.isCopyWriter()) {
                throw new IllegalArgumentException("Only copywriters could be assigned to review article");
            }
        });
        var title = new Title(articleName);
        var content = new Content(articleContent);
        var articleId = UUID.randomUUID();
        var review = new Review(UUID.randomUUID(), articleId, ReviewStatus.IN_PROGRESS, copyWriters);
        var newArticle = new Article(articleId, ArticleStatus.DRAFT, title, content, topics, review, publisher);
        return articleRepository.save(newArticle);
    }

    public suggestRemarks(SuggestedChanges suggestedChanges, UUID articleId) {

    }

}
