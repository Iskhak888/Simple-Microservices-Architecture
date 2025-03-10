package org.example.main;

//TODO: always remove unused imports, keep your file clean and neat
import org.example.main.Employee;
import org.example.main.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
public class EmployeeController {

    private final EmployeeRepository employeeRepo;

    public EmployeeController(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    /**
     * NOTE: bul kontrollerdin toluk urli  http://localhost:8082/employees emes,
     * tolugu http://localhost:8082/main/employees
     */

    // GET http://localhost:8082/employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    /**
     */
    // POST http://localhost:8082/employees
    // body: { "name":"John","position":"Developer" }
    @PostMapping("/new")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepo.save(employee);
    }
}
