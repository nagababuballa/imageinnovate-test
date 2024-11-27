package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    @Size(min = 1, message = "At least one phone number is required")
    private List<@Pattern(regexp = "^\\+?\\d{10,15}$", message = "Invalid phone number format") String> phoneNumbers;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in the format YYYY-MM-DD")
    private String doj;
    @Min(value = 1, message = "Salary must be positive")
    private double salary;
}
