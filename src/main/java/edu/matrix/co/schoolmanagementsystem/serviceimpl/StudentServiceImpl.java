package edu.matrix.co.schoolmanagementsystem.serviceimpl;

import edu.matrix.co.schoolmanagementsystem.dto.PaginationRequestDto;
import edu.matrix.co.schoolmanagementsystem.dto.PaginationResponseDto;
import edu.matrix.co.schoolmanagementsystem.dto.StudentDTO;
import edu.matrix.co.schoolmanagementsystem.entity.StudentEntity;
import edu.matrix.co.schoolmanagementsystem.repository.StudentRepository;
import edu.matrix.co.schoolmanagementsystem.services.StudentService;
import edu.matrix.co.schoolmanagementsystem.transformer.StudentTransformer;
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
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentTransformer studentTransformer;

    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        if (ObjectUtils.isEmpty(studentDTO)){
            throw new EntityNotFoundException("StudentDTO is null");
        }
        StudentEntity save = studentRepository.save(studentTransformer.toEntity(studentDTO));
        return studentTransformer.toDto(save);
    }

    @Override
    public void deleteById(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
        }
        throw new EntityNotFoundException("Student with id " + studentId + " not found");
    }

    @Override
    public StudentDTO update(Long studentId, StudentDTO changes) {
        if (ObjectUtils.isEmpty(studentId)){
            throw new EntityNotFoundException("StudentId is required");
        }
        StudentEntity studentEntity = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with id " + studentId + " not found"));
        StudentEntity updateEntity = studentTransformer.toUpdateEntity(changes, studentEntity);
        studentRepository.save(updateEntity);
        return studentTransformer.toDto(updateEntity);
    }

    @Override
    public PaginationResponseDto findAllPaginated(PaginationRequestDto requestDto) {
        // create page request
        Pageable pageRequest = PaginationUtils.createPageRequest(requestDto);

        // get data from repository
        Page<StudentEntity> pageResult = studentRepository.findAll(pageRequest);

        // build data from repository
        return PaginationUtils.buildPaginationResponse(pageResult);
    }

}
