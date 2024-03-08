package com.didt.pagesale.server;

import com.didt.pagesale.dto.CoursesDto;
import com.didt.pagesale.repository.CoursesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursesService {
    final ModelMapper mapper;
    final CoursesRepository coursesRepository;

    public CoursesService(ModelMapper mapper, CoursesRepository coursesRepository) {
        this.mapper = mapper;
        this.coursesRepository = coursesRepository;
    }

    public List<CoursesDto> getCourses() {
        var listCourses = coursesRepository.findAll();
        return listCourses.stream()
                .map(course -> mapper.map(course, CoursesDto.class))
                .collect(Collectors.toList());
    }
}
