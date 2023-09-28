package com.example.backendattornatus.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@SuperBuilder
@Data
public class HttpResponse {
    private String timeStamp;
    private Integer statusCode;
    private HttpStatus status;
    private String errorMessage;
    private String message;
    private Map<String , ?> data;
}
