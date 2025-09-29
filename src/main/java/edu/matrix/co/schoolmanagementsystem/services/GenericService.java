package edu.matrix.co.schoolmanagementsystem.services;

import edu.matrix.co.schoolmanagementsystem.dto.PaginationRequestDto;
import edu.matrix.co.schoolmanagementsystem.dto.PaginationResponseDto;

import java.util.List;

public interface GenericService<DTO, ID> {
    DTO save(DTO entity);
    void deleteById(ID id);
    List<DTO> findAll();
    DTO update(Long id, DTO changes);
    PaginationResponseDto findAllPaginated(PaginationRequestDto requestDto);
}