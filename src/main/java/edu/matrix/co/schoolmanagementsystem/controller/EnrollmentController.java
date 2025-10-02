package edu.matrix.co.schoolmanagementsystem.controller;

import edu.matrix.co.schoolmanagementsystem.dto.GradeDTO;
import edu.matrix.co.schoolmanagementsystem.dto.StudentDTO;
import edu.matrix.co.schoolmanagementsystem.entity.EnrollmentEntity;
import edu.matrix.co.schoolmanagementsystem.services.EnrollmentService;
import edu.matrix.co.schoolmanagementsystem.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("enrollments")
@AllArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("enrollStudent")
    public ResponseEntity<?> enroll(@PathVariable Long studentId, @RequestBody List<Long> courseIds){
        var enrollments = enrollmentService.enrollStudentInCourses(studentId, courseIds);
        return ResponseUtil.returnResponse(enrollments);
    }

    @PostMapping("findGradeByStudentIdAndCourseId")
    public ResponseEntity<?> grade(@Valid @RequestBody GradeDTO dto){
        var enrollment = enrollmentService.recordGrade(dto.getStudentId(), dto.getCourseId(), dto.getGrade());
        return ResponseUtil.returnResponse(enrollment);
    }

    @GetMapping("getStudentEnrollmentsByStudentId")
    public ResponseEntity<?> getStudentEnrollments(@RequestBody StudentDTO studentDTO){
        var studentId = studentDTO.getStudentId();
        var enrollments = enrollmentService.fetchStudentEnrollments(studentId);
        double gpa = enrollmentService.computeGPA(studentId);
        return ResponseUtil.returnResponse(
                new Object(){
                    public final List<EnrollmentEntity> records = enrollments;
                    public final double computedGPA = gpa;
                }
        );
    }
}

