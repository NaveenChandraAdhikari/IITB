package com.example.ASCiitB.service;

import com.example.ASCiitB.course.Course;
import com.example.ASCiitB.dto.CourseInstanceRequest;
import com.example.ASCiitB.entity.CourseInstance;
import com.example.ASCiitB.repository.CourseInstanceRepository;
import com.example.ASCiitB.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseInstanceRepository courseInstanceRepository;

    public Course addCourse(Course course){
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new NoSuchElementException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
    public CourseInstance addCourseInstance(Long courseId, int year, int semester) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        CourseInstance instance = new CourseInstance(course, year, semester);
        return courseInstanceRepository.save(instance);
    }


    public Page<Course> listAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); //for larger datasets
        return courseRepository.findAll(pageable);
    }
    public Page<CourseInstanceRequest> listCourseInstances(int year, int semester, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseInstance> instances = courseInstanceRepository.findByYearAndSemester(year, semester, pageable);

        return instances.map(this::convertToCourseInstanceDTO);
    }

    private CourseInstanceRequest convertToCourseInstanceDTO(CourseInstance instance) {
        CourseInstanceRequest dto = new CourseInstanceRequest();
        dto.setCourseId(instance.getId());
        dto.setCourseTitle(instance.getCourse().getTitle());
        dto.setCourseCode(instance.getCourse().getCode());
        dto.setYear(instance.getYear());
        dto.setSemester(instance.getSemester());
        return dto;
    }




    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + id));
    }

    public CourseInstance getCourseInstanceById(int year, int semester, Long id) {
        return courseInstanceRepository.findByYearAndSemesterAndId(year, semester, id)
                .orElseThrow(() -> new NoSuchElementException("Course instance not found"));



    }


    public void deleteCourseInstance(int year, int semester, Long id) {
        CourseInstance instance = getCourseInstanceById(year, semester, id);
        courseInstanceRepository.delete(instance);
    }







}



