package com.ead.course.services.exception;

public class CourseNotFoundException extends RuntimeException {
    private static final long seriaLVersionUID = 1L;
    public CourseNotFoundException(String msg) {
        super(msg);
    }
}
