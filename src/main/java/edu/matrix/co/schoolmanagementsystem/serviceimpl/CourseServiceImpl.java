package edu.matrix.co.schoolmanagementsystem.serviceimpl;

import edu.matrix.co.schoolmanagementsystem.dto.CourseDTO;
import edu.matrix.co.schoolmanagementsystem.dto.PaginationRequestDto;
import edu.matrix.co.schoolmanagementsystem.dto.PaginationResponseDto;
import edu.matrix.co.schoolmanagementsystem.entity.CourseEntity;
import edu.matrix.co.schoolmanagementsystem.repository.CourseRepository;
import edu.matrix.co.schoolmanagementsystem.services.GenericService;
import edu.matrix.co.schoolmanagementsystem.transformer.CourseTransformer;
import edu.matrix.co.schoolmanagementsystem.util.PaginationUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
@AllArgsConstructor
public class CourseServiceImpl implements GenericService<CourseDTO, Long> {
    private final CourseRepository courseRepository;
    private final CourseTransformer courseTransformer;

    @Override
    public CourseDTO save(CourseDTO courseDTO) {
        if (ObjectUtils.isEmpty(courseDTO)){
            throw new EntityNotFoundException("courseDTO is null");
        }
        CourseEntity save = courseRepository.save(courseTransformer.toEntity(courseDTO));
        return courseTransformer.toDto(save);
    }

    @Override
    public void deleteById(Long courseId) {
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
        }
        throw new EntityNotFoundException("course with id " + courseId + " not found");
    }

    @Override
    public CourseDTO update(Long studentId, CourseDTO changes) {
        CourseEntity courseEntity = courseRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with id " + studentId + " not found"));
        CourseEntity updateEntity = courseTransformer.toUpdateEntity(changes, courseEntity);
        courseRepository.save(updateEntity);
        return courseTransformer.toDto(updateEntity);
    }

    @Override
    public PaginationResponseDto findAllPaginated(PaginationRequestDto requestDto) {
        // create page request
        Pageable pageRequest = PaginationUtils.createPageRequest(requestDto);

        // get data from repository
        Page<CourseEntity> pageResult = courseRepository.findAll(pageRequest);

        // build data from repository
        return PaginationUtils.buildPaginationResponse(pageResult);
    }

}
