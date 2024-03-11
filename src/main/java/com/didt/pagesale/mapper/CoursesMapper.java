package com.didt.pagesale.mapper;

import com.didt.pagesale.dto.CoursesDto;
import com.didt.pagesale.model.Courses;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoursesMapper {
    CoursesDto entityToDto(Courses courses);

    List<CoursesDto> listEntityToListDto(List<Courses> coursesList);
}
