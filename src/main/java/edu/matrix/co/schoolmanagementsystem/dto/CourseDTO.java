package edu.matrix.co.schoolmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long courseId;
    @NotBlank(message = "courseCode is required")
    private String code;
    @NotBlank(message = "title is required")
    private String title;
    private String description;

    @Positive(message = "value must be in positive")
    private int capacity;

    @Positive(message = "value must be in positive")
    @NotBlank(message = "credits is required")
    private int credits;

    private Set<Long> prerequisiteIds;
}
