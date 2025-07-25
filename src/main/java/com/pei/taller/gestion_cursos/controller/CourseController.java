package com.pei.taller.gestion_cursos.controller;

import com.pei.taller.gestion_cursos.model.Course;
import com.pei.taller.gestion_cursos.repository.CourseRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseRepository courseRepository;

    public CourseController(@Autowired CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            return ResponseEntity.ok(optionalCourse.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el curso con id: " + id);
        }
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<?> getCourseByAuthor(@PathVariable String author) {
        String courseName = courseRepository.findByAuthor(author);
        if (courseName == null || courseName.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró un curso para el autor: " + author);
        }
        return ResponseEntity.ok(courseName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return ResponseEntity.ok("Curso eliminado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el curso con id: " + id);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllCourses() {
        try {
            if (courseRepository.count() == 0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay cursos para eliminar");
            } else {
                courseRepository.deleteAll();
                return ResponseEntity.ok("Todos los cursos han sido eliminados");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al intentar eliminar los cursos: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createCourses(@RequestBody List<Course> courses) {
        if (courses.isEmpty() || courses.stream().anyMatch(course -> course.getName() == null || course.getAuthor() == null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La lista de cursos está vacía");
        }
        List<Course> savedCourses = courseRepository.saveAll(courses);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable long id, @RequestBody Course course) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            if (course.getName() != null && !course.getName().isEmpty()) {
                existingCourse.setName(course.getName());
            }
            if (course.getAuthor() != null && !course.getAuthor().isEmpty()) {
                existingCourse.setAuthor(course.getAuthor());
            }
            Course updatedCourse = courseRepository.save(existingCourse);
            return ResponseEntity.ok(updatedCourse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el curso con id: " + id);
        }
    }
}
