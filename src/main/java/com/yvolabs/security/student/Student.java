package com.yvolabs.security.student;

import lombok.AllArgsConstructor;

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

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
