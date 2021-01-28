package com.yvolabs.security.student;

import org.springframework.security.access.prepost.PreAuthorize;
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

    /** PreAuthorize args: hasRole('ROLE_'), hasAnyRole('ROLE_'), hasAuthority('permission'), hasAnyAuthority('permission')  */

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_TRAINEE')")
    public List<Student> getAllStudents() {

        return STUDENTS;
    }

    @GetMapping("{studentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_TRAINEE')")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {

        return STUDENTS.stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("student " + studentId + " does not exist."));
    }


    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(String.format("ADDED: %s", student.toString()));
    }

    @DeleteMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println(String.format("DELETE: student with id %s deleted", studentId));
    }

    @PutMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {

        System.out.println(String.format("UPDATED: ID:%s %s", studentId, student.toString()));
    }
}
