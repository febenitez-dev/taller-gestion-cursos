package com.pei.taller.gestion_cursos;

import com.pei.taller.gestion_cursos.controller.CourseController;
import com.pei.taller.gestion_cursos.model.Course;
import com.pei.taller.gestion_cursos.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.fasterxml.jackson.databind.ObjectMapper; // Necesario para convertir a JSON


import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {
    @MockitoBean
    private CourseRepository courseRepositoryMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_ReturnAllCoursesOk_When_GetAllCourses() throws Exception {
        List<Course> courses = List.of(
                new Course(1L, "Fernando Luchessi", "Spring Boot"),
                new Course(2L, "Fernando Luchessi","Mockito Basics")
        );

        when(courseRepositoryMock.findAll()).thenReturn(courses);

        // Act & Assert
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idCourse").value(1L))
                .andExpect(jsonPath("$[0].author").value("Fernando Luchessi"))
                .andExpect(jsonPath("$[0].name").value("Spring Boot"))
                .andExpect(jsonPath("$[1].idCourse").value(2L))
                .andExpect(jsonPath("$[1].author").value("Fernando Luchessi"))
                .andExpect(jsonPath("$[1].name").value("Mockito Basics"));
    }

    @Test
    void should_ReturnNoContent_When_NoCoursesExist() throws Exception {
        // Arrange
        when(courseRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/courses"))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_CreateCourses_When_ValidCoursesSent() throws Exception {
        List<Course> inputCourses = List.of(
                new Course("Fernando Luchessi", "Spring Boot"),
                new Course("Fernando Luchessi", "Mockito Basics")
        );

        List<Course> savedCourses = List.of(
                new Course(1L, "Fernando Luchessi", "Spring Boot"),
                new Course(2L, "Fernando Luchessi", "Mockito Basics")
        );

        when(courseRepositoryMock.saveAll(inputCourses)).thenReturn(savedCourses);

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputCourses)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idCourse").value(1L))
                .andExpect(jsonPath("$[0].author").value("Fernando Luchessi"))
                .andExpect(jsonPath("$[0].name").value("Spring Boot"))
                .andDo(print());
    }

    @Test
    void should_ReturnBadRequest_When_EmptyOrInvalidCoursesSent() throws Exception {
        List<Course> invalidCourses = List.of(
                new Course(null, "Spring Boot"), // author null
                new Course("Fernando", null)     // name null
        );

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCourses)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La lista de cursos está vacía"))
                .andDo(print());
    }
}
