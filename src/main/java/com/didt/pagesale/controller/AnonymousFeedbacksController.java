package com.didt.pagesale.controller;

import com.didt.pagesale.dto.FeedbacksDto;
import com.didt.pagesale.server.FeedbacksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/anonymous/feedbacks")
public class AnonymousFeedbacksController {
    private final FeedbacksService feedbacksService;

    public AnonymousFeedbacksController(FeedbacksService feedbacksService) {
        this.feedbacksService = feedbacksService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<FeedbacksDto>> getList() {
        return new ResponseEntity<>(feedbacksService.getList(), HttpStatus.OK);
    }
}
