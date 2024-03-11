package com.didt.pagesale.controller;

import com.didt.pagesale.dto.FeedbacksDto;
import com.didt.pagesale.server.FeedbacksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<FeedbacksDto> create(@RequestBody FeedbacksDto input) {
        return new ResponseEntity<>(feedbacksService.create(input), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody FeedbacksDto input) {
        return new ResponseEntity<>(feedbacksService.update(input), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(feedbacksService.delete(id), HttpStatus.OK);
    }
}
