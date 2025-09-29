package edu.matrix.co.schoolmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private Long enrollmentId;
    @NotNull(message = "student is required")
    private StudentDTO student;

    @NotNull(message = "course is required")
    private CourseDTO course;
    private Double grade;
    private String status;
}
