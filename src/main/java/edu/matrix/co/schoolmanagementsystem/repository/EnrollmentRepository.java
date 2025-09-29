package edu.matrix.co.schoolmanagementsystem.repository;

import edu.matrix.co.schoolmanagementsystem.entity.CourseEntity;
import edu.matrix.co.schoolmanagementsystem.entity.EnrollmentEntity;
import edu.matrix.co.schoolmanagementsystem.entity.StudentEntity;
import edu.matrix.co.schoolmanagementsystem.enums.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    Optional<EnrollmentEntity> findByStudentAndCourse(StudentEntity student, CourseEntity course);
    List<EnrollmentEntity> findByStudent(StudentEntity student);
    long countByCourseAndStudentStatus(CourseEntity course, StudentStatus status);
}
