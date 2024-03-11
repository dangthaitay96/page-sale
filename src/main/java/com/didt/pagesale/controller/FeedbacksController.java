package com.didt.pagesale.controller;

import com.didt.pagesale.dto.CoursesDto;
import com.didt.pagesale.dto.FeedbacksCreateDto;
import com.didt.pagesale.dto.FeedbacksDto;
import com.didt.pagesale.response.FileUploadResponse;
import com.didt.pagesale.server.CoursesService;
import com.didt.pagesale.server.FeedbacksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbacksController {
    private final FeedbacksService feedbacksService;

    public FeedbacksController(FeedbacksService feedbacksService) {
        this.feedbacksService = feedbacksService;
    }
    @GetMapping("/list")
    public ResponseEntity<List<FeedbacksDto>> geList() {
        return new ResponseEntity<>(feedbacksService.getList(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<FeedbacksDto> create(@RequestBody FeedbacksCreateDto input) {
        FeedbacksDto createdFeedback = feedbacksService.create(input);
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }
  }
