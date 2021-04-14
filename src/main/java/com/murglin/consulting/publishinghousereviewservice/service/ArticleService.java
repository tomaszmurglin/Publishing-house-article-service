package com.murglin.consulting.publishinghousereviewservice.service;

import com.murglin.consulting.publishinghousereviewservice.exception.NotFoundException;
import com.murglin.consulting.publishinghousereviewservice.exception.UnauthorizedException;
import com.murglin.consulting.publishinghousereviewservice.model.article.Article;
import com.murglin.consulting.publishinghousereviewservice.model.article.Content;
import com.murglin.consulting.publishinghousereviewservice.model.article.Title;
import com.murglin.consulting.publishinghousereviewservice.model.article.Topic;
import com.murglin.consulting.publishinghousereviewservice.model.review.Review;
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
                                       UUID authorId) {
        //TODO validation of input data in layer above before (eg. json schema) or after deserialization (eg. jackson)
        var author = userRepository.findById(authorId).orElseThrow(() -> new NotFoundException("User has not been found"));
        if (author.isJournalist()) { //TODO authorization could be moved into controllers layer of the app
            throw new UnauthorizedException("Only Journalist can submit article for publishing");
        }
        if (!topicRepository.allExists(topics)) {
            throw new NotFoundException("Some of provided topics have not been found");
        }
        userRepository.findByIdIn(copyWriters).forEach(copyWriter -> {
            var user = copyWriter.orElseThrow(() -> new NotFoundException("User has not been found"));
            if (!user.isCopyWriter()) {
                throw new IllegalArgumentException("Only copywriters could be assigned to review article");
            }
        });
        var title = Title.create(articleName);
        var content = Content.create(articleContent);
        var review = Review.create(copyWriters);
        var newArticle = Article.create(title, content, topics, review, author);
        return articleRepository.save(newArticle);
    }

    public Article suggestChanges(UUID copyWriterId, String remarks, UUID articleId) {
        //TODO validation of input data in layer above before (eg. json schema) or after deserialization (eg. jackson)
        var copyWriter = userRepository.findById(copyWriterId).orElseThrow(() -> new NotFoundException("User has not been found"));
        if (copyWriter.isCopyWriter()) { //TODO authorization could be moved into controllers layer of the app
            throw new UnauthorizedException("Only Copywriter can suggest changes to an article");
        }
        var suggestChanges = SuggestedChanges.create(copyWriterId, remarks);
        var article = articleRepository.findById(articleId).orElseThrow(() -> new NotFoundException("Article has not been found"));
        article.suggestChanges(suggestChanges);
        return articleRepository.save(article);
    }

    public Article publish() {

    }
}
