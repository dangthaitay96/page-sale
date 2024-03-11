package com.didt.pagesale.server;

import com.didt.pagesale.dto.CoursesDto;
import com.didt.pagesale.exception.FileStorageException;
import com.didt.pagesale.model.Courses;
import com.didt.pagesale.repository.CoursesRepository;
import com.didt.pagesale.response.FileUploadResponse;
import com.didt.pagesale.utils.FileUploadUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.cleanPath;

@Slf4j
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

        String fileCode = FileUploadUtil.saveFile(root, fileName, multipartFile);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/courses/files/" + fileCode);

        Courses courses = new Courses();
        courses.setName(name);
        courses.setDescription(description);
        courses.setImage("/courses/files/" + fileCode);
        coursesRepository.save(courses);

        return response;
    }

    public FileUploadResponse update(Long id, MultipartFile multipartFile, String description, String name) throws IOException {
        Courses courses = coursesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (multipartFile != null) {
            String fileName = cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            long size = multipartFile.getSize();
            String fileCode = FileUploadUtil.saveFile(root, fileName, multipartFile);
            courses.setImage("/courses/files/" + fileCode);

        }
        courses.setDescription(description);
        courses.setName(name);
        coursesRepository.save(courses);
        return null;
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("Could not read the file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new FileStorageException("Error accessing file: " + filename, e);
        }
    }

    public Long delete(Long id) {
        String path = coursesRepository.findById(id).get().getImage();

        int lastIndexOfSlash = path.lastIndexOf('/');
        String fileNameToDelete = path.substring(lastIndexOfSlash + 1);
        try {
            Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.getFileName().toString().equals(fileNameToDelete)) {
                        Files.delete(file);
                        log.info("Deleted file: " + file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        coursesRepository.deleteById(id);
        return id;
    }
}
