package edu.matrix.co.schoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.matrix.co.schoolmanagementsystem.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Status dto.
 *
 * @author sarmad ali shaikh
 * @since 15-05-2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDto {

    private Long totalRecords;
    private Integer totalPages;
    private StatusEnum code;
    private String message;
    private Object data;

    public String getCode() {
        return code.value() + "";
    }

}