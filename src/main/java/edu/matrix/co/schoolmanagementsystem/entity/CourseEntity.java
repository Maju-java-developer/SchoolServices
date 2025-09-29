package edu.matrix.co.schoolmanagementsystem.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "courses")
@Data
@NoArgsConstructor @AllArgsConstructor
public class CourseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_code", nullable = false, unique = true, length = 10)
    private String code;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(length = 500, name = "description")
    private String description;

    @Column(nullable = false, name = "capacity")
    private int capacity;

    @Column(nullable = false)
    private int credits;

    // prerequisites: many-to-many self-referencing
    @ManyToMany
    @JoinTable(
            name = "course_prereq",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "prereq_id"))
    private Set<CourseEntity> prerequisites = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EnrollmentEntity> enrollments = new HashSet<>();
}

