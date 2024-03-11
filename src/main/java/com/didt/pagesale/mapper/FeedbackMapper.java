package com.didt.pagesale.mapper;

import com.didt.pagesale.dto.FeedbacksDto;
import com.didt.pagesale.model.Feedbacks;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    FeedbacksDto mapEtoD(Feedbacks feedbacks);

    Feedbacks mapDtoE(FeedbacksDto feedbacksDto);

    List<Feedbacks> mapListDtoListE(List<FeedbacksDto> feedbacksDtos);

    List<FeedbacksDto> mapListEtoListD(List<Feedbacks> feedbacksList);
}
