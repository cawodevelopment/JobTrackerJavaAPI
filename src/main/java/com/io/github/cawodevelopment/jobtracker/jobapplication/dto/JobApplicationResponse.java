package com.io.github.cawodevelopment.jobtracker.jobapplication.dto;

import com.io.github.cawodevelopment.jobtracker.jobapplication.JobStatus;
import com.io.github.cawodevelopment.jobtracker.jobapplication.JobType;

import java.time.LocalDate;

public record JobApplicationResponse(

        Long id,
        String jobUrl,
        JobType jobType,
        String location,
        int salaryMin,
        int salaryMax,
        LocalDate dateApplied,
        LocalDate deadline,
        JobStatus status,
        String notes

) {
}