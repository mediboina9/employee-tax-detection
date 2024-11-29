package com.Imaginnovate.employee_tax_detection.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonDeserialize(as = EmployeeTaxDetection.class)
public class EmployeeTaxDetection {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private double yearlySalary;
    private double taxAmount;
    private double CessAmount;
}
