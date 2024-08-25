package com.example.ASCiitB.dto;

import lombok.Data;

@Data
public class CourseInstanceRequest {

    private Long courseId;
    private int year;
    private int semester;
    private String courseTitle;
    private String courseCode;

    // Getters and setters
}