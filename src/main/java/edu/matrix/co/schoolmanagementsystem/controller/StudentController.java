package edu.matrix.co.schoolmanagementsystem.controller;

import edu.matrix.co.schoolmanagementsystem.dto.PaginationRequestDto;
import edu.matrix.co.schoolmanagementsystem.dto.PaginationResponseDto;
import edu.matrix.co.schoolmanagementsystem.dto.StudentDTO;
import edu.matrix.co.schoolmanagementsystem.services.StudentService;
import edu.matrix.co.schoolmanagementsystem.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("students")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("createStudent")
    public ResponseEntity<?> create(@Valid @RequestBody StudentDTO dto){
        StudentDTO save = studentService.save(dto);
        return ResponseUtil.returnResponse(save);
    }

    @PostMapping("deleteById")
    public ResponseEntity<?> deleteById(@RequestBody StudentDTO studentDTO){
        studentService.deleteById(studentDTO.getStudentId());
        return ResponseUtil.returnResponse(studentDTO);
    }

    @PostMapping("findAllWithPaginate")
    public ResponseEntity<?> list(@RequestBody PaginationRequestDto requestDto){
        PaginationResponseDto allPaginated = studentService.findAllPaginated(requestDto);
        return ResponseUtil.returnResponse(allPaginated);
    }

    @PutMapping("updateStudent")
    public ResponseEntity<?> update(@RequestBody StudentDTO dto){
        StudentDTO update = studentService.update(dto.getStudentId(), dto);
        return ResponseUtil.returnResponse(update);
    }

}

