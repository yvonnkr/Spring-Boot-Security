package com.yvolabs.security.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
    private final static List<Student> STUDENTS = Arrays.asList(
            new Student(1, "John Doe"),
            new Student(2, "Jane Doe"),
            new Student(3, "Mary Smith")

    );

    @GetMapping
    public List<Student> getAllStudents() {

        return STUDENTS;
    }

    @GetMapping("{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {

        return STUDENTS.stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("student " + studentId + " does not exist."));
    }


    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(String.format("ADDED: %s", student.toString()));
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println(String.format("DELETE: student with id %s deleted", studentId));
    }

    @PutMapping("{studentId}")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {

        System.out.println(String.format("UPDATED: ID:%s %s", studentId, student.toString()));
    }
}
