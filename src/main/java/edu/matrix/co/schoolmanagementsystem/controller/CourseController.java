package edu.matrix.co.schoolmanagementsystem.controller;

import edu.matrix.co.schoolmanagementsystem.dto.CourseDTO;
import edu.matrix.co.schoolmanagementsystem.dto.PaginationRequestDto;
import edu.matrix.co.schoolmanagementsystem.services.CourseService;
import edu.matrix.co.schoolmanagementsystem.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping("createCourse")
    public ResponseEntity<?> create(@Valid @RequestBody CourseDTO dto){
        return ResponseEntity.ok(courseService.save(dto));
    }

    @GetMapping("findAllPaginated")
    public ResponseEntity<?> list(PaginationRequestDto requestDto){
        return ResponseEntity.ok(courseService.findAllPaginated(requestDto));
    }

    @PutMapping("/updateCourse")
    public ResponseEntity<?> update(@RequestBody CourseDTO dto){
        return ResponseEntity.ok(courseService.update(dto.getCourseId(), dto));
    }

    @DeleteMapping("/deleteByCourseId")
    public ResponseEntity<?> delete(@RequestBody CourseDTO dto){
        courseService.deleteById(dto.getCourseId());
        return ResponseUtil.returnResponse("Deleted Course");
    }

}
