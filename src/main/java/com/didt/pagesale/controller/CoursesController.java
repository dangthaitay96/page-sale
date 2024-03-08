package com.didt.pagesale.controller;

import com.didt.pagesale.dto.CoursesDto;
import com.didt.pagesale.server.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
