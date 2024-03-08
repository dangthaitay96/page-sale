package com.didt.pagesale.server;

import com.didt.pagesale.dto.CoursesDto;
import com.didt.pagesale.model.Courses;
import com.didt.pagesale.repository.CoursesRepository;
import com.didt.pagesale.response.FileUploadResponse;
import com.didt.pagesale.utils.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.cleanPath;

@Service
public class CoursesService {
    private final Path root = Paths.get("files");
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
        response.setDownloadUri("/courses/files/" + filecode);


        Courses courses = new Courses();
        courses.setName(name);
        courses.setDescription(description);
        courses.setImage("/courses/files/"+ filecode);
        coursesRepository.save(courses);

        return response;
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
