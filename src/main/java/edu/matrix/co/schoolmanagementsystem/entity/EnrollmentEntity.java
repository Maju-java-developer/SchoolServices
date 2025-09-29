package edu.matrix.co.schoolmanagementsystem.entity;

import edu.matrix.co.schoolmanagementsystem.enums.StudentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "enrollments")
@Data
@NoArgsConstructor @AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id","course_id"}))
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StudentEntity student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CourseEntity course;

    private Double grade;

    @Enumerated(EnumType.STRING)
    private StudentStatus studentStatus = StudentStatus.ENROLLED;
}
