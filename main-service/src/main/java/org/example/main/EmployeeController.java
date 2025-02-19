package org.example.main;

import org.example.main.Employee;
import org.example.main.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepo;

    public EmployeeController(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    // GET http://localhost:8082/employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    // POST http://localhost:8082/employees
    // body: { "name":"John","position":"Developer" }
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepo.save(employee);
    }
}
