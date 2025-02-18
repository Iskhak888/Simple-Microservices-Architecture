package org.example.main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @GetMapping("/public/hello")
    public String hello() {
        return "Hello, this is a public endpoint!";
    }

    @GetMapping("/employee/list")
    public String getAllEmployees() {
        // Это приватный эндпоинт
        // (фактически защищается на уровне Gateway,
        //  но можно и тут дополнительно проверять токен)
        return "Employee list: [John, Alice, Bob]";
    }
}