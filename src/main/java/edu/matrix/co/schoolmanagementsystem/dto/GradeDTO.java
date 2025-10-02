package edu.matrix.co.schoolmanagementsystem.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double grade;
    private Long courseId;
    private Long studentId;
}
