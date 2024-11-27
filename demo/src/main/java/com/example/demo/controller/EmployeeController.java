package com.example.demo.controller;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.ResponseVO;
import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import com.example.demo.swagger.dto.SwaggerEmployeeResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
    *This method is used for creating new employee upon given data
    * in the Request body
     * @return  ResponseEntity of ResponseVO of Employee that contains the Employee information along with the proper header codes
    */
    @Operation(summary = "")
    @ApiResponses({
            @ApiResponse(content = @Content(schema = @Schema(implementation = SwaggerEmployeeResponseVO.class)),responseCode = "200",description = "Employee Created Successfully")
    })
    @PostMapping
    public ResponseEntity<ResponseVO<Employee>> createEmployee(@RequestBody EmployeeRequest employee){
        ResponseVO<Employee> employeeResponse =  employeeService.createEmployee(employee);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

}
