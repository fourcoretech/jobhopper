package com.interview.prep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.interview.prep", "com.resume.common.library"})
public class InterviewPrepApplication {
    public static void main(String[] args) {
        SpringApplication.run(InterviewPrepApplication.class, args);
    }
}
