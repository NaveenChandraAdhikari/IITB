package com.example.ASCiitB.entity;


import com.example.ASCiitB.course.Course;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class CourseInstance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

/*
One common cause of deep nesting in JSON serialization is circular references between objects. For example, if CourseInstance references Course and Course references CourseInstance, it can create an infinite loop when trying to serialize these objects to JSON.

Solution:

Use @JsonManagedReference and @JsonBackReference or @JsonIgnore to manage circular references in your entity classes.
 */
    @ManyToOne
    @JoinColumn(name="course_id",nullable = false)
    @JsonBackReference
    private Course course;

    @Column(name = "course_year")
//    for avoiding conflict as in h2 year is there in documentation
    private int year;
    private int semester;



//
//    @Transient
//    private String courseTitle;
//
//    @Transient
//    private String courseCode;

    public CourseInstance(){}

    public CourseInstance(Course course, int year, int semester) {
        this.course = course;
        this.year = year;
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "CourseInstance [id=" + id + ", course=" + course.getCode() + ", year=" + year + ", semester=" + semester + "]";
    }

}

/*
In the CourseInstance entity:

The @ManyToOne annotation on course indicates that each CourseInstance is associated with one Course.
The @JoinColumn(name = "course_id") specifies that the foreign key in the CourseInstance table that references the Course table is named course_id.


In JPA (Java Persistence API), having a no-argument constructor is crucial because JPA uses reflection to instantiate entities. It requires a no-argument constructor to create instances of your entity class.


 */