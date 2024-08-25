package com.example.ASCiitB.repository;

import com.example.ASCiitB.entity.CourseInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseInstanceRepository extends JpaRepository<CourseInstance,Long> {


    Page<CourseInstance> findByYearAndSemester(int year, int semester, Pageable pageable);



    Optional<CourseInstance> findByYearAndSemesterAndId(int year, int semester, Long id);
}







/*
 Without a repository interface, you’d need to write a manual Data Access Object (DAO) layer. This involves creating classes that handle database operations using EntityManager or JdbcTemplate,,You’d need to implement methods for CRUD operations, write SQL queries, handle transactions, and manage the persistence context

 */