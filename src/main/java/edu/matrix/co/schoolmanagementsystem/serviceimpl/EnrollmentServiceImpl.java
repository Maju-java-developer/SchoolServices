package edu.matrix.co.schoolmanagementsystem.serviceimpl;

import edu.matrix.co.schoolmanagementsystem.entity.CourseEntity;
import edu.matrix.co.schoolmanagementsystem.entity.EnrollmentEntity;
import edu.matrix.co.schoolmanagementsystem.entity.StudentEntity;
import edu.matrix.co.schoolmanagementsystem.enums.StudentStatus;
import edu.matrix.co.schoolmanagementsystem.repository.CourseRepository;
import edu.matrix.co.schoolmanagementsystem.repository.EnrollmentRepository;
import edu.matrix.co.schoolmanagementsystem.repository.StudentRepository;
import edu.matrix.co.schoolmanagementsystem.services.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepo;
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;

    /**
     * Enroll student in multiple courses in a single transaction, enforcing prereqs and capacity.
     */
    @Transactional
    @Override
    public List<EnrollmentEntity> enrollStudentInCourses(Long studentId, List<Long> courseIds){
        StudentEntity student = studentRepo.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        List<CourseEntity> courses = courseRepo.findAllById(courseIds);
        if (courses.size() != new HashSet<>(courseIds).size()) {
            throw new IllegalArgumentException("One or more courses not found");
        }

        // Validate prerequisites for each course
        for (CourseEntity course : courses) {
            for (CourseEntity prereq : course.getPrerequisites()) {
                boolean hasCompleted = student.getEnrollments().stream()
                        .filter(e -> e.getCourse().getCourseId().equals(prereq.getCourseId()))
                        .anyMatch(e -> e.getStudentStatus() == StudentStatus.COMPLETED);
                if (!hasCompleted) {
                    throw new IllegalStateException("Student does not meet prerequisite for " + course.getCode() + ": missing " + prereq.getCode());
                }
            }

            // Validate capacity: check number of ENROLLED records vs capacity
            long enrolledCount = enrollmentRepo.countByCourseAndStudentStatus(course, StudentStatus.ENROLLED);
            if (course.getCapacity() > 0 && enrolledCount >= course.getCapacity()) {
                throw new IllegalStateException("Course " + course.getCode() + " is at capacity");
            }
        }

        // Create enrollments
        List<EnrollmentEntity> created = new ArrayList<>();
        for (CourseEntity course : courses) {
            // check duplicate
            Optional<EnrollmentEntity> existing = enrollmentRepo.findByStudentAndCourse(student, course);
            if (existing.isPresent()) {
                created.add(existing.get());
                continue;
            }
            EnrollmentEntity e = EnrollmentEntity.builder()
                    .student(student)
                    .course(course)
                    .studentStatus(StudentStatus.ENROLLED)
                    .build();
            created.add(enrollmentRepo.save(e));
            student.getEnrollments().add(created.get(created.size()-1));
            course.getEnrollments().add(created.get(created.size()-1));
        }
        return created;
    }

    /**
     * Record grade for a student in a course. Student must be enrolled.
     * Only allow updates if status is ENROLLED (per requirement: allow grade updates only if not completed).
     * Recording a grade marks the Enrollment as COMPLETED.
     */
    @Transactional
    @Override
    public EnrollmentEntity recordGrade(Long studentId, Long courseId, Double grade){
        StudentEntity student = studentRepo.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        CourseEntity course = courseRepo.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        EnrollmentEntity enrollment = enrollmentRepo.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new IllegalStateException("Student is not enrolled in the course"));

        if (enrollment.getStudentStatus() == StudentStatus.COMPLETED) {
            throw new IllegalStateException("Course already completed; cannot update grade");
        }

        // grade validation is handled by bean validation on DTO; enforce range here too
        if (grade < 0 || grade > 100) throw new IllegalArgumentException("Grade must be between 0 and 100");

        enrollment.setGrade(grade);
        enrollment.setStudentStatus(StudentStatus.COMPLETED);
        return enrollmentRepo.save(enrollment);
    }

    @Override
    public List<EnrollmentEntity> fetchStudentEnrollments(Long studentId){
        StudentEntity s = studentRepo.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        return enrollmentRepo.findByStudent(s);
    }

    @Override
    public double computeGPA(Long studentId){
        List<EnrollmentEntity> enrollments = fetchStudentEnrollments(studentId).stream()
                .filter(e -> e.getGrade() != null)
                .toList();
        if (enrollments.isEmpty()) return 0.0;
        double totalPoints = 0.0;
        int totalCredits = 0;
        for (EnrollmentEntity e : enrollments) {
            int credits = e.getCourse().getCredits();
            double grade = e.getGrade();
            double points = (grade / 100.0) * 4.0;
            totalPoints += points * credits;
            totalCredits += credits;
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
}
