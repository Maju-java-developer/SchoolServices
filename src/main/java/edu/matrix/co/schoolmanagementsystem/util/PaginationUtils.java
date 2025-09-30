package edu.matrix.co.schoolmanagementsystem.util;

import edu.matrix.co.schoolmanagementsystem.dto.PaginationRequestDto;
import edu.matrix.co.schoolmanagementsystem.dto.PaginationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

public class PaginationUtils {
    public static Pageable createPageRequest(PaginationRequestDto paginationRequestDto) {
        if (paginationRequestDto == null) {
            paginationRequestDto = new PaginationRequestDto();
        }
        Integer page = ObjectUtils.isEmpty(paginationRequestDto.getCurrentPage()) ? 0 : paginationRequestDto.getCurrentPage();
        Integer itemsPerPage = ObjectUtils.isEmpty(paginationRequestDto.getItemsPerPages()) ? 50 : paginationRequestDto.getItemsPerPages();
        String sortBy = ObjectUtils.isEmpty(paginationRequestDto.getSortBy()) ? "createdDate" : paginationRequestDto.getSortBy();
        String direction = ObjectUtils.isEmpty(paginationRequestDto.getDirection()) ? "ASC" : paginationRequestDto.getDirection();
        return PageRequest.of(page,
                itemsPerPage,
                (direction.equalsIgnoreCase("DESC") ||
                        direction.equalsIgnoreCase("DSC") ?
                        Sort.Direction.DESC : Sort.Direction.ASC), sortBy);
    }

    public static PaginationResponseDto buildPaginationResponse(Page<?> page) {
        PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
        paginationResponseDto.setTotalPages(page.getTotalPages());
        paginationResponseDto.setTotalRecords(page.getTotalElements());
        paginationResponseDto.setData(page.getContent());
        return paginationResponseDto;
    }

}
