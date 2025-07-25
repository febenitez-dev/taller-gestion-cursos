package com.pei.taller.gestion_cursos.model;

import jakarta.persistence.*;

@Entity
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCourse;

    private String author;

    private String name;

    public long getIdCourse() {
        return idCourse;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course() { }

    public Course(String author, String name) {
        this.author = author;
        this.name = name;
    }

    public Course(long idCourse, String author, String name) {
        this.idCourse = idCourse;
        this.author = author;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Course{" +
                "idCourse=" + idCourse +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
