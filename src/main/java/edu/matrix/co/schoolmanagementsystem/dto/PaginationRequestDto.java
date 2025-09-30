package edu.matrix.co.schoolmanagementsystem.dto;

import lombok.Data;

@Data
public class PaginationRequestDto {
    private Integer currentPage;
    private Integer itemsPerPages;
    private String sortBy;
    private String direction;
}
