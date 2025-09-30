package edu.matrix.co.schoolmanagementsystem.util;

import edu.matrix.co.schoolmanagementsystem.dto.StatusDto;
import edu.matrix.co.schoolmanagementsystem.enums.StatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseUtil {

    /**
     * @param exception
     * @return response
     */
    public static ResponseEntity<Object> returnResponse(RuntimeException exception) {
        StatusDto statusDto = new StatusDto();
        statusDto.setCode(StatusEnum.FAILURE);
        statusDto.setMessage(exception.getMessage());
        exception.printStackTrace();
        return new ResponseEntity<>(statusDto, HttpStatus.OK);
    }

    /**
     * @param exception
     * @return response
     */
    public static ResponseEntity<Object> returnResponse(Exception exception) {
        StatusDto statusDto = new StatusDto();
        statusDto.setCode(StatusEnum.FAILURE);
        statusDto.setMessage(exception.getMessage());
        exception.printStackTrace();
        return new ResponseEntity<>(statusDto, HttpStatus.OK);
    }

    public static ResponseEntity<Object> returnResponseWithCustomMessage(Integer code, String message, Object data) {
        StatusDto statusDto = new StatusDto();
        statusDto.setCode(StatusEnum.SUCCESS);
        statusDto.setMessage(message);
        statusDto.setData(data);

        return new ResponseEntity<>(statusDto, HttpStatus.valueOf(code));
    }

    /**
     * @param object
     * @return response
     */
    public static ResponseEntity<Object> returnResponse(Object object) {
        StatusDto statusDto = new StatusDto();
        statusDto.setCode(StatusEnum.SUCCESS);
        statusDto.setMessage(StatusEnum.SUCCESS.getReasonPhrase());
        statusDto.setData(object);
        return new ResponseEntity<>(statusDto, HttpStatus.OK);
    }


}