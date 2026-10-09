package com.io.github.cawodevelopment.jobtracker.jobapplication.dto;

import com.io.github.cawodevelopment.jobtracker.jobapplication.JobStatus;
import com.io.github.cawodevelopment.jobtracker.jobapplication.JobType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record JobApplicationRequest(

        @NotBlank
        @Size(max = 2048)
        String jobUrl,

        @NotNull
        JobType jobType,

        @NotBlank
        @Size(max = 255)
        String location,

        @PositiveOrZero
        int salaryMin,

        @PositiveOrZero
        int salaryMax,

        @PastOrPresent
        LocalDate dateApplied,

        @FutureOrPresent
        LocalDate deadline,

        @NotNull
        JobStatus status,

        @Size(max = 5000)
        String notes

) {
}