package com.yvolabs.security.student;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class Student {
    private final Integer studentId;
    private final String studentName;

    public Integer getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }
}
