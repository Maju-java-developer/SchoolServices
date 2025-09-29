package edu.matrix.co.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponseDto {
    private int code;
    private String message;
    private Long totalRecords;
    private Integer totalPages;
    private List<?> data;

}
