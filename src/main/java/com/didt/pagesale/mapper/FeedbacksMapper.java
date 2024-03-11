package com.didt.pagesale.mapper;

import com.didt.pagesale.dto.FeedbacksCreateDto;
import com.didt.pagesale.dto.FeedbacksDto;
import com.didt.pagesale.model.Feedbacks;
import org.mapstruct.Mapping;

public interface FeedbacksMapper {

    @Mapping(target = "status", constant = "1")
    Feedbacks map(FeedbacksCreateDto input);

    FeedbacksDto mapToDto(Feedbacks feedbacks);
}