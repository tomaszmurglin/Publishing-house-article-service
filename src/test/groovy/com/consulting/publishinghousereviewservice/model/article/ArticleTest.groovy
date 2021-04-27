package com.consulting.publishinghousereviewservice.model.article

import com.murglin.consulting.publishinghousereviewservice.model.article.*
import com.murglin.consulting.publishinghousereviewservice.model.review.Review
import com.murglin.consulting.publishinghousereviewservice.model.review.SuggestedChange
import com.murglin.consulting.publishinghousereviewservice.model.user.User
import com.murglin.consulting.publishinghousereviewservice.model.user.UserRole
import spock.lang.Specification

class ArticleTest extends Specification {

    def "Author cannot suggest remarks to its own article"() {
        given: "Theres an article"
        def title = Title.create("Example article")
        def content = Content.create("Some content")
        def topic = Topic.create("Some topic")
        def review = Review.create(Set.of(UUID.randomUUID()))
        def author = new User(UserRole.COPYWRITER)
        def article = Article.create(title, content, topic, review, author)

        when: "Author try to suggest remarks to its own article"
        def suggestedChanges = SuggestedChange.create(author.getId(), "some remarks")
        article.suggestChanges(suggestedChanges)

        then: "exception IllegalArgumentException should be thrown"
        def exception = thrown(IllegalStateException)
        exception.message == "Author cannot suggest remarks to its own article"
        article.getStatus() == ArticleStatus.DRAFT
        article.getReview().getSuggestedChanges().isEmpty()
    }

    def "Cannot suggest changes to already published article"() {
        given: "Theres an article"
        def title = Title.create("Example article")
        def content = Content.create("Some content")
        def topic = Topic.create("Some topic")
        def review = Review.create(Set.of(UUID.randomUUID()))
        def author = new User(UserRole.COPYWRITER)
        def article = Article.create(title, content, topic, review, author)
        article.setStatus(ArticleStatus.PUBLISHED)

        when: "Author try to suggest remarks to its own article"
        def suggestedChanges = SuggestedChange.create(UUID.randomUUID(), "some remarks")
        article.suggestChanges(suggestedChanges)

        then: "exception IllegalArgumentException should be thrown"
        def exception = thrown(IllegalStateException)
        exception.message == "Cannot suggest changes to already published article"
        article.getStatus() == ArticleStatus.PUBLISHED
        article.getReview().getSuggestedChanges().isEmpty()
    }

    def "Copywriter successfully suggest changes"() {
        given: "Theres an article"
        def title = Title.create("Example article")
        def content = Content.create("Some content")
        def topic = Topic.create("Some topic")
        def reviewerId = UUID.randomUUID()
        def review = Review.create(Set.of(reviewerId))
        def author = new User(UserRole.COPYWRITER)
        def article = Article.create(title, content, topic, review, author)
        article.setStatus(ArticleStatus.DRAFT)

        when: "Author try to suggest remarks to its own article"
        def suggestedChanges = SuggestedChange.create(reviewerId, "some remarks")
        article.suggestChanges(suggestedChanges)

        then:
        article.getStatus() == ArticleStatus.IN_REVIEW
        !article.getReview().getSuggestedChanges().isEmpty()
        article.getReview().getSuggestedChanges().contains(suggestedChanges)
    }

    def "Only author can publish the article"() {
        given: "Theres an article"
        def title = Title.create("Example article")
        def content = Content.create("Some content")
        def topic = Topic.create("Some topic")
        def reviewerId = UUID.randomUUID()
        def review = Review.create(Set.of(reviewerId))
        def author = new User(UserRole.COPYWRITER)
        def article = Article.create(title, content, topic, review, author)
        article.setStatus(ArticleStatus.IN_REVIEW)

        when: "Publisher is not an author"
        article.publish(new User(UserRole.COPYWRITER))

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Only author can publish the article"
        article.getStatus() == ArticleStatus.IN_REVIEW
    }

    def "Draft article can not be published"() {
        given: "Theres an article"
        def title = Title.create("Example article")
        def content = Content.create("Some content")
        def topic = Topic.create("Some topic")
        def reviewerId = UUID.randomUUID()
        def review = Review.create(Set.of(reviewerId))
        def author = new User(UserRole.COPYWRITER)
        def article = Article.create(title, content, topic, review, author)
        article.setStatus(ArticleStatus.DRAFT)

        when: "Publisher is not an author"
        article.publish(author)

        then:
        def exception = thrown(IllegalStateException)
        exception.message == "Only in - review article can be published"
        article.getStatus() == ArticleStatus.DRAFT
    }

    def "Already published article can not be published again"() {
        given: "Theres an article"
        def title = Title.create("Example article")
        def content = Content.create("Some content")
        def topic = Topic.create("Some topic")
        def reviewerId = UUID.randomUUID()
        def review = Review.create(Set.of(reviewerId))
        def author = new User(UserRole.COPYWRITER)
        def article = Article.create(title, content, topic, review, author)
        article.setStatus(ArticleStatus.PUBLISHED)

        when: "Publisher is not an author"
        article.publish(author)

        then:
        def exception = thrown(IllegalStateException)
        exception.message == "Only in - review article can be published"
        article.getStatus() == ArticleStatus.PUBLISHED
    }

    def "Author successfully published an article"() {
        given: "Theres an article"
        def title = Title.create("Example article")
        def content = Content.create("Some content")
        def topic = Topic.create("Some topic")
        def reviewerId = UUID.randomUUID()
        def review = Review.create(Set.of(reviewerId))
        def author = new User(UserRole.COPYWRITER)
        def article = Article.create(title, content, topic, review, author)
        article.setStatus(ArticleStatus.IN_REVIEW)

        when: "Publisher is not an author"
        article.publish(author)

        then:
        article.getStatus() == ArticleStatus.PUBLISHED
    }

    def "Article is successfully created"() {
        given: "Theres an article"
        def title = Title.create("Example article")
        def content = Content.create("Some content")
        def topic = Topic.create("Some topic")
        def reviewerId = UUID.randomUUID()
        def review = Review.create(Set.of(reviewerId))
        def author = new User(UserRole.COPYWRITER)

        when:
        def article = Article.create(title, content, topic, review, author)

        then:
        article.getTitle() == title
        article.getContent() == content
        article.getTopic() == topic
        article.getReview() == review
        article.getAuthor() == author
    }
}
