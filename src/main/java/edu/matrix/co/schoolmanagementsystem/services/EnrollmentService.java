package edu.matrix.co.schoolmanagementsystem.services;

import edu.matrix.co.schoolmanagementsystem.entity.EnrollmentEntity;

import java.util.List;

public interface EnrollmentService {
    List<EnrollmentEntity> enrollStudentInCourses(Long studentId, List<Long> courseIds);
    EnrollmentEntity recordGrade(Long studentId, Long courseId, Double grade);
    List<EnrollmentEntity> fetchStudentEnrollments(Long studentId);
    double computeGPA(Long studentId);
}
