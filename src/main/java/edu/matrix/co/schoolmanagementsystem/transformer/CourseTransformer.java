package edu.matrix.co.schoolmanagementsystem.transformer;

import edu.matrix.co.schoolmanagementsystem.dto.CourseDTO;
import edu.matrix.co.schoolmanagementsystem.entity.CourseEntity;
import edu.matrix.co.schoolmanagementsystem.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseTransformer implements Transformer<CourseDTO, CourseEntity> {

    private final CourseRepository courseRepository;

    @Override
    public CourseDTO toDto(CourseEntity entity) {
        if (entity == null) return null;

        Set<Long> prereqIds = entity.getPrerequisites()
                .stream()
                .map(CourseEntity::getCourseId)
                .collect(Collectors.toSet());

        return new CourseDTO(
                entity.getCourseId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCapacity(),
                entity.getCredits(),
                prereqIds
        );
    }

    @Override
    public CourseEntity toEntity(CourseDTO dto) {
        if (dto == null) return null;

        Set<CourseEntity> prereqCourses = new HashSet<>();
        if (dto.getPrerequisiteIds() != null) {
            prereqCourses = new HashSet<>(courseRepository.findAllById(dto.getPrerequisiteIds()));
        }

        return new CourseEntity(
                dto.getCourseId(),
                dto.getCode(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getCapacity(),
                dto.getCredits(),
                prereqCourses,
                new HashSet<>()
        );
    }

    @Override
    public CourseEntity toUpdateEntity(CourseDTO dto, CourseEntity entity) {
        if (dto == null || entity == null) return entity;

        if (dto.getCode() != null) entity.setCode(dto.getCode());
        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getCapacity() > 0) entity.setCapacity(dto.getCapacity());
        if (dto.getCredits() > 0) entity.setCredits(dto.getCredits());

        if (dto.getPrerequisiteIds() != null) {
            Set<CourseEntity> prereqs = new HashSet<>(courseRepository.findAllById(dto.getPrerequisiteIds()));
            entity.setPrerequisites(prereqs);
        }

        return entity;
    }

    @Override
    public List<CourseDTO> toDto(List<CourseEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
