package com.xpf.rxjavaretrofit2demo.bean;

import java.util.List;

/**
 * Created by x-sir on 2019-05-25 :)
 * Function:
 */
public class Student {

    private String name;
    private List<Course> courses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public static class Course {
        private String courseName;
        private String courseTeacher;

        public Course() {
        }

        public Course(String courseName) {
            this.courseName = courseName;
        }

        public Course(String courseName, String courseTeacher) {
            this.courseName = courseName;
            this.courseTeacher = courseTeacher;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseTeacher() {
            return courseTeacher;
        }

        public void setCourseTeacher(String courseTeacher) {
            this.courseTeacher = courseTeacher;
        }
    }
}
