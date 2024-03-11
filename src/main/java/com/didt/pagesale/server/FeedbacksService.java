package com.didt.pagesale.server;

import com.didt.pagesale.dto.FeedbacksDto;
import com.didt.pagesale.mapper.FeedbackMapper;
import com.didt.pagesale.repository.FeedbacksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FeedbacksService {
    final FeedbacksRepository feedbacksRepository;
    final FeedbackMapper feedbackMapper;

    public FeedbacksService(FeedbacksRepository feedbacksRepository, FeedbackMapper feedbackMapper) {
        this.feedbacksRepository = feedbacksRepository;
        this.feedbackMapper = feedbackMapper;
    }


    public List<FeedbacksDto> getList() {
        var listFeed = feedbacksRepository.findAll();
        return feedbackMapper.mapListEtoListD(listFeed);
    }

    public FeedbacksDto create(FeedbacksDto input) {
        input.setStatus(1);
        input.setCreatedDate(new Date());
        input.setCreatedBy("admin");
        var feedbacks = feedbackMapper.mapDtoE(input);
        feedbacksRepository.save(feedbacks);

        return null;
    }

    public String update(FeedbacksDto input) {
        input.setStatus(1);
        input.setCreatedDate(new Date());
        input.setCreatedBy("admin");
        var feedbacks = feedbackMapper.mapDtoE(input);
        feedbacksRepository.save(feedbacks);
        return "ok";
    }

    public Long delete(Long id) {
        feedbacksRepository.deleteById(id);
        return id;
    }
}
