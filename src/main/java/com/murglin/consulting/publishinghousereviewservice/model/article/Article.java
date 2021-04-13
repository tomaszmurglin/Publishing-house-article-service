package com.murglin.consulting.publishinghousereviewservice.model.article;

import com.murglin.consulting.publishinghousereviewservice.model.review.Review;
import com.murglin.consulting.publishinghousereviewservice.model.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class Article {

    private final UUID id;

    private final ArticleStatus status;

    private final Title title;

    private final Content content;

    private final Set<Topic> topics;

    private final Review review;

    private final User author;
}
