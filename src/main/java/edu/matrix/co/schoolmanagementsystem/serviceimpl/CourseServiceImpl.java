package edu.matrix.co.schoolmanagementsystem.serviceimpl;

import edu.matrix.co.schoolmanagementsystem.dto.CourseDTO;
import edu.matrix.co.schoolmanagementsystem.dto.PaginationRequestDto;
import edu.matrix.co.schoolmanagementsystem.dto.PaginationResponseDto;
import edu.matrix.co.schoolmanagementsystem.entity.CourseEntity;
import edu.matrix.co.schoolmanagementsystem.repository.CourseRepository;
import edu.matrix.co.schoolmanagementsystem.services.CourseService;
import edu.matrix.co.schoolmanagementsystem.transformer.CourseTransformer;
import edu.matrix.co.schoolmanagementsystem.util.PaginationUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseTransformer courseTransformer;

    @Override
    public CourseDTO save(CourseDTO courseDTO) {
        if (ObjectUtils.isEmpty(courseDTO)){
            throw new EntityNotFoundException("courseDTO is null");
        }
        CourseEntity entity = courseTransformer.toEntity(courseDTO);

        // handle prerequisites inside service, not controller
        if (courseDTO.getPrerequisiteIds() != null && !courseDTO.getPrerequisiteIds().isEmpty()) {
            Set<CourseEntity> prereqs = new HashSet<>(courseRepository.findAllById(courseDTO.getPrerequisiteIds()));
            entity.setPrerequisites(prereqs);
        }

        CourseEntity saved = courseRepository.save(entity);
        return courseTransformer.toDto(saved);
    }

    @Override
    public void deleteById(Long courseId) {
        if (ObjectUtils.isEmpty(courseId))
            throw new EntityNotFoundException("courseId is required");
        if (!courseRepository.existsById(courseId))
            throw new EntityNotFoundException("Course with id " + courseId + " not found");

        courseRepository.deleteById(courseId);
    }

    @Override
    public CourseDTO update(Long courseId, CourseDTO changes) {
        if (ObjectUtils.isEmpty(courseId))
            throw new EntityNotFoundException("courseId is required");

        CourseEntity existing = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id " + courseId + " not found"));

        CourseEntity updatedEntity = courseTransformer.toUpdateEntity(changes, existing);

        // handle prerequisites update
        if (changes.getPrerequisiteIds() != null) {
            Set<CourseEntity> prereqs = new HashSet<>(courseRepository.findAllById(changes.getPrerequisiteIds()));
            updatedEntity.setPrerequisites(prereqs);
        }

        CourseEntity saved = courseRepository.save(updatedEntity);
        return courseTransformer.toDto(saved);
    }

    @Override
    public PaginationResponseDto findAllPaginated(PaginationRequestDto requestDto) {
        Pageable pageRequest = PaginationUtils.createPageRequest(requestDto);
        Page<CourseEntity> pageResult = courseRepository.findAll(pageRequest);
        return PaginationUtils.buildPaginationResponse(pageResult);
    }

}
