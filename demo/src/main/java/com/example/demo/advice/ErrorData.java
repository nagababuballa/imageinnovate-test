package com.example.demo.advice;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorData {

    private int statusCode;
    private String reason;
    private String message;
    private LocalDate date;
    private Map<String,Object> errors;

}
