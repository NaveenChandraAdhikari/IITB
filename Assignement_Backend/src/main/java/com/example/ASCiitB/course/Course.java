package com.example.ASCiitB.course;


import com.example.ASCiitB.entity.CourseInstance;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String code;

    private String description;

//Cascade Delete: Update your Course entity to cascade delete operations to CourseInstance,,This will automatically delete all related CourseInstance records when a Course is deleted
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseInstance> instances;
    public Course() {
    }

    public Course(String title, String code, String description) {
        this.title = title;
        this.code = code;
        this.description = description;
    }


    @Override
    public String toString() {
        return "Course [id=" + id + ", title=" + title + ", code=" + code + ", description=" + description + "]";
    }


}
