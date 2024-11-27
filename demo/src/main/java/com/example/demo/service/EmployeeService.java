package com.example.demo.service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.ResponseVO;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    /**
     *This method is used for creating new employee
     * in db by taking request body from the controller
     * @return  ResponseVO of Employee that contains the Employee information along with the success and user readable message
     */
    public ResponseVO<Employee> createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = convertDtoToEntity(employeeRequest);
        employee.setPhoneNumbers(employeeRequest.getPhoneNumbers().stream().collect(Collectors.joining(",")));
      employee = employeeRepository.save(employee);
      return ResponseVO.<Employee>builder()
              .success(true)
              .message("Employee Created Successfully")
              .data(employee)
              .build();
    }

    private Employee convertDtoToEntity(EmployeeRequest employeeRequest) {
    return Employee.builder()
            .doj(employeeRequest.getDoj())
            .email(employeeRequest.getEmail())
            .salary(employeeRequest.getSalary())
            .employeeId(employeeRequest.getEmployeeId())
            .firstName(employeeRequest.getFirstName())
            .lastName(employeeRequest.getLastName())
            .build();
    }
}
