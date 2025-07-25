package com.pei.taller.gestion_cursos;

import com.pei.taller.gestion_cursos.controller.CourseController;
import com.pei.taller.gestion_cursos.model.Course;
import com.pei.taller.gestion_cursos.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseControllerTest {
    private CourseController courseController;

    @Mock
    private CourseRepository courseRepositoryMock;

    Course expectedCourse;

    @BeforeEach
    void setUp() {
        courseController = new CourseController(courseRepositoryMock);

        expectedCourse = new Course(1L, "Fernando Lucchesi", "Introduction to Computer Science");
    }

    @Test
    void should_ReturnCourse_When_CorrectId() {

    }
}
