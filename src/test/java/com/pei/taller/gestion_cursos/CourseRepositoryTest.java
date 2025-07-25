package com.pei.taller.gestion_cursos;

import com.pei.taller.gestion_cursos.model.Course;
import com.pei.taller.gestion_cursos.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void should_ReturnCourse_When_CorrectId() {
        // GIVEN
        Course expectedCourse = new Course(1L, "Fernando Lucchesi", "Introduction to Computer Science");
       long id = 1L;

        // WHEN
        Optional<Course> actualCourse = courseRepository.findById(id);

        // THEN
        assertAll(
                () -> assertTrue(actualCourse.isPresent()),
                () -> assertEquals(expectedCourse.getIdCourse(), actualCourse.get().getIdCourse()),
                () -> assertEquals(expectedCourse.getAuthor(), actualCourse.get().getAuthor()),
                () -> assertEquals(expectedCourse.getName(), actualCourse.get().getName())
        );
    }

    @Test
    void should_ReturnCourses_When_FindAll() {
        // GIVEN
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1L, "Fernando Lucchesi", "Introduction to Computer Science"));
        courses.add(new Course(2L, "Gonzalo Giacomino", "Introduction to Java"));
        courses.add(new Course(3L, "Lautaro Soria", "Introduction to Spring Boot"));
        courses.add(new Course(4L, "Delfina Borrelli", "Introduction to REST APIs"));
        courses.add(new Course(5L, "Elian Benitez", "Introduction to Microservices"));
        courses.add(new Course(6L, "Agustion Barbeito", "Introduction to Docker"));

        // WHEN
        List<Course> actualCourses = courseRepository.findAll();

        // THEN
        assertArrayEquals(courses.toArray(), actualCourses.toArray());
    }
}
