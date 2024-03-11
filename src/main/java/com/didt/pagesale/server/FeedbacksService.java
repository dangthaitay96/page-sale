package com.didt.pagesale.server;

import com.didt.pagesale.dto.FeedbacksCreateDto;
import com.didt.pagesale.dto.FeedbacksDto;
import com.didt.pagesale.dto.FeedbacksUpdateDto;
import com.didt.pagesale.model.Feedbacks;
import com.didt.pagesale.repository.FeedbacksRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FeedbacksService {
    final ModelMapper mapper;
    final FeedbacksRepository feedbacksRepository;

    public FeedbacksService(ModelMapper mapper, FeedbacksRepository feedbacksRepository) {
        this.mapper = mapper;
        this.feedbacksRepository = feedbacksRepository;
    }

    public List<FeedbacksDto> getList() {
        var itemList = feedbacksRepository.findAll();
        return itemList.stream()
                .map(item -> mapper.map(item, FeedbacksDto.class))
                .collect(Collectors.toList());
    }

    public FeedbacksDto create(FeedbacksCreateDto input) {
        Feedbacks feedbacks = new Feedbacks();
        feedbacks.setPersonName(input.personName);
        feedbacks.setContent(input.content);
        feedbacks.setStatus(1);
        feedbacks.setCreatedDate(new Date());
        feedbacks.setCreatedBy("admin");
        feedbacks = feedbacksRepository.save(feedbacks);
        return mapper.map(feedbacks, FeedbacksDto.class);
    }

    public FeedbacksDto update(FeedbacksUpdateDto input) {
        Feedbacks feedbacks = new Feedbacks();
        feedbacks.setPersonName(input.personName);
        feedbacks.setContent(input.content);
        feedbacks.setStatus(1);
        feedbacks.setCreatedDate(new Date());
        feedbacks.setCreatedBy("admin");
        feedbacks = feedbacksRepository.save(feedbacks);
        return mapper.map(feedbacks, FeedbacksDto.class);
    }

    public Long delete(Long id) {
        feedbacksRepository.deleteById(id);
        return id;
    }

    public FeedbacksDto update(Long id, FeedbacksUpdateDto input) {
        Feedbacks existingFeedback = feedbacksRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
        existingFeedback.setPersonName(input.personName);
        existingFeedback.setContent(input.content);
        existingFeedback.setModifiedDate(new Date());
        existingFeedback.setModifiedBy("admin");
        Feedbacks updatedFeedback = feedbacksRepository.save(existingFeedback);
        return mapper.map(updatedFeedback, FeedbacksDto.class);
    }
}