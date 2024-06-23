package com.didt.pagesale.controller;

import com.didt.pagesale.dto.CoursesDto;
import com.didt.pagesale.response.FileUploadResponse;
import com.didt.pagesale.server.CoursesService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

@RestController
@RequestMapping("/courses")
public class CoursesController {
  private final CoursesService coursesService;

  public CoursesController(CoursesService coursesService) {
    this.coursesService = coursesService;
  }

  @GetMapping("/listCourses")
  public ResponseEntity<List<CoursesDto>> getCourses() {
    return new ResponseEntity<>(coursesService.getCourses(), HttpStatus.OK);
  }

  @GetMapping("/paging")
  public ResponseEntity<Page<CoursesDto>> getCourses(Pageable pageable) {
    Page<CoursesDto> courses = coursesService.getCourses(pageable);
    return ResponseEntity.ok().body(courses);
  }

  @PostMapping("/create")
  public ResponseEntity<FileUploadResponse> createCourses(
      @RequestParam("file") MultipartFile multipartFile,
      @RequestParam String description,
      @RequestParam String name)
      throws IOException {
    return new ResponseEntity<>(
        coursesService.upload(multipartFile, description, name), HttpStatus.OK);
  }

  @PutMapping("/update")
  public ResponseEntity<?> update(
      @RequestParam(value = "file", required = false) MultipartFile multipartFile,
      @RequestParam String description,
      @RequestParam Long id,
      @RequestParam String name)
      throws IOException {
    return new ResponseEntity<>(
        coursesService.update(id, multipartFile, description, name), HttpStatus.OK);
  }

  @GetMapping("/files/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = coursesService.load(filename);
    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\""
                + UriUtils.encode(file.getFilename(), StandardCharsets.UTF_8)
                + "\"")
        .body(file);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteCourses(@PathVariable(value = "id") Long id) {
    return new ResponseEntity<>(coursesService.delete(id), HttpStatus.OK);
  }
}
