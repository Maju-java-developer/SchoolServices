package edu.matrix.co.schoolmanagementsystem.services;

import edu.matrix.co.schoolmanagementsystem.dto.PaginationRequestDto;
import edu.matrix.co.schoolmanagementsystem.dto.PaginationResponseDto;

public interface GenericService<DTO, ID> {
    DTO save(DTO dto);
    void deleteById(ID id);
    DTO update(Long id, DTO changes);
    PaginationResponseDto findAllPaginated(PaginationRequestDto requestDto);
}