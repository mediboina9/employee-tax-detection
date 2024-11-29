package com.Imaginnovate.employee_tax_detection.controller;

import com.Imaginnovate.employee_tax_detection.model.EmployeeDetails;
import com.Imaginnovate.employee_tax_detection.model.EmployeeTaxDetection;
import com.Imaginnovate.employee_tax_detection.repository.EmployeeRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class Controller {
    @Autowired
    EmployeeRepository repository;


    @PostMapping("/details")
    public ResponseEntity<Void> StoreEmployeeDetails(@RequestBody EmployeeDetails employeeDetails){
        repository.save(employeeDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/taxDetection")
    public ResponseEntity<List<EmployeeTaxDetection>> getEmployeeTaxDetection(){
        List <EmployeeDetails> employeeDetailsList = repository.findAll();

        List<EmployeeTaxDetection> employeeTaxDetectionList = new ArrayList<>();
        for (EmployeeDetails employeeDetails : employeeDetailsList) {
            employeeTaxDetectionList.add(calculateTax(employeeDetails));
        }

        return ResponseEntity.status(HttpStatus.OK).body(employeeTaxDetectionList);

    }

    private EmployeeTaxDetection calculateTax(EmployeeDetails employeeDetails) {
        double monthlySalary = employeeDetails.getSalary();
        LocalDate doj = LocalDate.of(employeeDetails.getDOJ().getYear(), employeeDetails.getDOJ().getMonth(), employeeDetails.getDOJ().getDay());
        LocalDate financialYearStart = LocalDate.of(2024, 4, 1);
        LocalDate financialYearEnd = LocalDate.of(2025, 3, 31);

        LocalDate salaryStartDate = doj.isAfter(financialYearStart) ? doj : financialYearStart;

        int monthsWorked = (int) ChronoUnit.MONTHS.between(YearMonth.from(salaryStartDate), YearMonth.from(financialYearEnd)) + 1;

        int daysInStartMonth = salaryStartDate.getDayOfMonth() - 1;
        double lossOfPay = daysInStartMonth > 0 ? (monthlySalary / 30) * daysInStartMonth : 0;

        double yearlySalary = (monthlySalary * monthsWorked) - lossOfPay;

        double totalTax = 0;
        if (yearlySalary > 250000) {
            totalTax += Math.min(250000, yearlySalary - 250000) * 0.05;
        }
        if (yearlySalary > 500000) {
            totalTax += Math.min(500000, yearlySalary - 500000) * 0.10;
        }
        if (yearlySalary > 1000000) {
            totalTax += (yearlySalary - 1000000) * 0.20;
        }

        double cess = 0;
        if (yearlySalary > 2500000) {
            cess = (yearlySalary - 2500000) * 0.02;
        }

        return new EmployeeTaxDetection(employeeDetails.getEmployeeId(), employeeDetails.getFirstName(),
            employeeDetails.getLastName(), yearlySalary, totalTax, cess);
    }
}
