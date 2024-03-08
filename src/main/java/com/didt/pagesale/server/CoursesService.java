package com.didt.pagesale.server;

import com.didt.pagesale.dto.CoursesDto;
import com.didt.pagesale.model.Courses;
import com.didt.pagesale.repository.CoursesRepository;
import com.didt.pagesale.response.FileUploadResponse;
import com.didt.pagesale.utils.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.cleanPath;

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

    public FileUploadResponse upload(MultipartFile multipartFile, String description, String name) throws IOException {
        String fileName = cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        long size = multipartFile.getSize();

        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);

        Courses courses = new Courses();
        courses.setName(name);
        courses.setDescription(description);
        courses.setImage(response.getDownloadUri());
        coursesRepository.save(courses);

        return response;
    }
}
