package org.example.main;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    private String name;
    private String position;

    // Constructors
    public Employee() {}
    public Employee(String name, String position) {
        this.name = name;
        this.position = position;
    }

    // ...
}
