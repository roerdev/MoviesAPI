package com.roerdev.moviesapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.roerdev.moviesapi.utils.DateUtils;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    private String status;
    private Object data;
    private String message;
    private List<?> list;
    private Page<?> page;
    private Date timestamp;
    private UUID id;

    public ResponseDTO() {
        super();
    }

    public ResponseDTO(String status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public ResponseDTO(String status, Page<?> page, String message) {
        this.status = status;
        this.page = page;
        this.message = message;
    }

    public ResponseDTO(String status, String message, UUID id) {
        this.status = status;
        this.message = message;
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp != null ? timestamp : DateUtils.getToFullDay();
    }


}
