package com.didt.pagesale.server;

import com.didt.pagesale.dto.CoursesDto;
import com.didt.pagesale.dto.FeedbacksCreateDto;
import com.didt.pagesale.dto.FeedbacksDto;
import com.didt.pagesale.mapper.FeedbacksMapper;
import com.didt.pagesale.model.Courses;
import com.didt.pagesale.model.Feedbacks;
import com.didt.pagesale.repository.FeedbacksRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
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
}