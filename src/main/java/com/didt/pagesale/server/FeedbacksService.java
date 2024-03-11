package com.didt.pagesale.server;

import com.didt.pagesale.dto.FeedbacksDto;
import com.didt.pagesale.mapper.FeedbackMapper;
import com.didt.pagesale.repository.FeedbacksRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FeedbacksService {
    final ModelMapper mapper;
    final FeedbacksRepository feedbacksRepository;

    final FeedbackMapper feedbackMapper;

    public FeedbacksService(ModelMapper mapper, FeedbacksRepository feedbacksRepository, FeedbackMapper feedbackMapper) {
        this.mapper = mapper;
        this.feedbacksRepository = feedbacksRepository;
        this.feedbackMapper = feedbackMapper;
    }

    public List<FeedbacksDto> getList() {
        var itemList = feedbacksRepository.findAll();
        return itemList.stream()
                .map(item -> mapper.map(item, FeedbacksDto.class))
                .collect(Collectors.toList());
    }

    public FeedbacksDto create(FeedbacksDto input) {
        var feedbacks = feedbackMapper.mapDtoE(input);
        feedbacks.setStatus(1);
        feedbacks.setCreatedDate(new Date());
        feedbacks.setCreatedBy("admin");
        feedbacksRepository.save(feedbacks);

        return null;
    }

    public FeedbacksDto update(FeedbacksDto input) {
        var feedbacks = feedbackMapper.mapDtoE(input);
        feedbacks.setStatus(1);
        feedbacks.setCreatedDate(new Date());
        feedbacks.setCreatedBy("admin");
        feedbacksRepository.save(feedbacks);

        return input;
    }

    public Long delete(Long id) {
        feedbacksRepository.deleteById(id);
        return id;
    }
}
