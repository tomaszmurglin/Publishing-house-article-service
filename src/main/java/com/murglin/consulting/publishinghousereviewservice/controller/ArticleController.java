package com.murglin.consulting.publishinghousereviewservice.controller;

import com.murglin.consulting.publishinghousereviewservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
}
