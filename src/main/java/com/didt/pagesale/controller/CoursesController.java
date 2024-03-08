package com.didt.pagesale.controller;

import com.didt.pagesale.dto.CoursesDto;
import com.didt.pagesale.response.FileUploadResponse;
import com.didt.pagesale.server.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CoursesController {
    @Autowired
    private CoursesService coursesService;

    @GetMapping("/listCourses")
    public ResponseEntity<List<CoursesDto>> getCourses() {
        return new ResponseEntity<>(coursesService.getCourses(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<FileUploadResponse> createCourses(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam String description,
            @RequestParam String name) throws IOException {
        return new ResponseEntity<>(coursesService.upload(multipartFile, description, name), HttpStatus.OK);
    }
}
