package com.example.ASCiitB.controller;


import com.example.ASCiitB.course.Course;
import com.example.ASCiitB.dto.CourseInstanceRequest;
import com.example.ASCiitB.entity.CourseInstance;
import com.example.ASCiitB.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Courses API
    @PostMapping("/courses")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course newCourse = courseService.addCourse(course);
        return ResponseEntity.ok(newCourse);
    }



//    debugg gg
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }



    @PostMapping("/instances")
    public ResponseEntity<CourseInstance> addCourseInstance(@RequestBody CourseInstanceRequest request) {
        CourseInstance newInstance = courseService.addCourseInstance(
                request.getCourseId(), request.getYear(), request.getSemester());
        return ResponseEntity.ok(newInstance);
    }


    @DeleteMapping("/instances/{year}/{semester}/{id}")
    public ResponseEntity<?> deleteCourseInstance(
            @PathVariable int year,
            @PathVariable int semester,
            @PathVariable Long id) {
        courseService.deleteCourseInstance(year, semester, id);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/courses")
    public ResponseEntity<Page<Course>> listCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Course> courses = courseService.listAllCourses(page, size);
        return ResponseEntity.ok(courses);
    }



    @GetMapping("/instances/{year}/{semester}")
    public ResponseEntity<Page<CourseInstanceRequest>> listCourseInstances(
            @PathVariable int year,
            @PathVariable int semester,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CourseInstanceRequest> instances = courseService.listCourseInstances(year, semester, page, size);
        return ResponseEntity.ok(instances);
    }


//View details of a particular course
@GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseDetails(@PathVariable Long id){
        Course course =courseService.getCourseById(id);
        return ResponseEntity.ok(course);
}

//View details of a particular course instance
    @GetMapping("/instances/{year}/{semester}/{id}")
    public ResponseEntity<CourseInstance> getCourseInstanceDetails(
            @PathVariable int year,
            @PathVariable int semester,
            @PathVariable Long id) {
        CourseInstance instance = courseService.getCourseInstanceById(year, semester, id);
        return ResponseEntity.ok(instance);
    }

}
