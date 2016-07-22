package com.muks.employee.employee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 15692 on 04/07/16.
 */

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    public EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public int getProjectedEmployeeCount() {
        int actualEmployeeCount = employeeService.getEmployeeCount();
        return actualEmployeeCount * 2;
    }

    public void saveEmployee(Employee employee) {
        employeeService.saveEmployee(employee);
    }

}
