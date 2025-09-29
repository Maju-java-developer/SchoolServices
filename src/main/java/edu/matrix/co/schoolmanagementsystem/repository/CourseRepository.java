package edu.matrix.co.schoolmanagementsystem.repository;

import edu.matrix.co.schoolmanagementsystem.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    boolean existsByCode(String code);
}
