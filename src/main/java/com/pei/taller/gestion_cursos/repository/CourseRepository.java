package com.pei.taller.gestion_cursos.repository;

import com.pei.taller.gestion_cursos.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByAuthor(String author);
}
