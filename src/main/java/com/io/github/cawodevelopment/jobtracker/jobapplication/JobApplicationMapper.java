package com.io.github.cawodevelopment.jobtracker.jobapplication;

import com.io.github.cawodevelopment.jobtracker.jobapplication.dto.JobApplicationRequest;
import com.io.github.cawodevelopment.jobtracker.jobapplication.dto.JobApplicationResponse;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationMapper {

    public JobApplication toEntity(JobApplicationRequest request) {
        JobApplication job = new JobApplication();

        job.setJobUrl(request.jobUrl());
        job.setJobType(request.jobType());
        job.setLocation(request.location());
        job.setSalaryMin(request.salaryMin());
        job.setSalaryMax(request.salaryMax());
        job.setDateApplied(request.dateApplied());
        job.setDeadline(request.deadline());
        job.setStatus(request.status());
        job.setNotes(request.notes());

        return job;
    }

    public JobApplicationResponse toResponse(JobApplication job) {
        return new JobApplicationResponse(
                job.getId(),
                job.getJobUrl(),
                job.getJobType(),
                job.getLocation(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getDateApplied(),
                job.getDeadline(),
                job.getStatus(),
                job.getNotes()
        );
    }

    public void updateEntity(JobApplication job, JobApplicationRequest request) {
        job.setJobUrl(request.jobUrl());
        job.setJobType(request.jobType());
        job.setLocation(request.location());
        job.setSalaryMin(request.salaryMin());
        job.setSalaryMax(request.salaryMax());
        job.setDateApplied(request.dateApplied());
        job.setDeadline(request.deadline());
        job.setStatus(request.status());
        job.setNotes(request.notes());
    }
}