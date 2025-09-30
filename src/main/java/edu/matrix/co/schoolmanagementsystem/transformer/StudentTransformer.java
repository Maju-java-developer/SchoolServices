package edu.matrix.co.schoolmanagementsystem.transformer;

import edu.matrix.co.schoolmanagementsystem.dto.StudentDTO;
import edu.matrix.co.schoolmanagementsystem.entity.StudentEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentTransformer implements Transformer<StudentDTO, StudentEntity> {

    @Override
    public StudentDTO toDto(StudentEntity studentEntity) {
        if (studentEntity == null) return null;
        return new StudentDTO(
                studentEntity.getStudentId(),
                studentEntity.getFirstName(),
                studentEntity.getLastName(),
                studentEntity.getEmail()
        );
    }

    @Override
    public StudentEntity toEntity(StudentDTO dto) {
        return toEntity(dto, new StudentEntity());
    }

    @Override
    public StudentEntity toUpdateEntity(StudentDTO studentDTO, StudentEntity studentEntity) {
        return toEntity(studentDTO, studentEntity);
    }

    @Override
    public List<StudentDTO> toDto(List<StudentEntity> studentEntities) {
        List<StudentDTO> studentDTOs = new ArrayList<>();
        if (ObjectUtils.isEmpty(studentEntities)) return null;
        for (StudentEntity studentEntity : studentEntities) {
            studentDTOs.add(toDto(studentEntity));
        }
        return studentDTOs;
    }

    private StudentEntity toEntity(StudentDTO studentDTO, StudentEntity studentEntity) {
        if (studentEntity == null) return null;
        if (ObjectUtils.isEmpty(studentDTO.getStudentId())){
            studentEntity.setStudentId(studentDTO.getStudentId());
        }
        if (ObjectUtils.isEmpty(studentDTO.getFirstName())){
            studentEntity.setFirstName(studentDTO.getFirstName());
        }
        if (ObjectUtils.isEmpty(studentDTO.getLastName())){
            studentEntity.setLastName(studentDTO.getLastName());
        }
        if (ObjectUtils.isEmpty(studentDTO.getEmail())){
            studentEntity.setEmail(studentDTO.getEmail());
        }
        return studentEntity;
    }
}
