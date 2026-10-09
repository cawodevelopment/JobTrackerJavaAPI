package com.io.github.cawodevelopment.jobtracker.jobapplication;

import com.io.github.cawodevelopment.jobtracker.jobapplication.dto.JobApplicationRequest;
import com.io.github.cawodevelopment.jobtracker.jobapplication.dto.JobApplicationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper jobApplicationMapper;

    public JobApplicationService(JobApplicationRepository jobApplicationRepository, JobApplicationMapper jobApplicationMapper) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobApplicationMapper = jobApplicationMapper;
    }

    public Page<JobApplicationResponse> getJobApplications(Pageable pageable) {
        return jobApplicationRepository.findAll(pageable)
                .map(jobApplication -> jobApplicationMapper
                        .toResponse(jobApplication));
    }

    public JobApplicationResponse getJobApplicationById(Long id) {
        JobApplication jobApplication = jobApplicationRepository
                .findById(id)
                .orElseThrow();

        return jobApplicationMapper.toResponse(jobApplication);
    }

    public JobApplicationResponse createJobApplication(JobApplicationRequest request) {
        JobApplication jobApplication = jobApplicationMapper.toEntity(request);

        JobApplication savedJobApplication = jobApplicationRepository.save(jobApplication);

        return jobApplicationMapper.toResponse(savedJobApplication);
    }

    public JobApplicationResponse updateJobApplicationById(JobApplicationRequest request, Long id) {
        JobApplication jobApplication = jobApplicationRepository
                .findById(id)
                .orElseThrow();

        jobApplicationMapper.updateEntity(jobApplication, request);

        JobApplication savedJobApplication = jobApplicationRepository.save(jobApplication);

        return jobApplicationMapper.toResponse(savedJobApplication);
    }

    public void deleteJobApplicationById(Long id) {
        jobApplicationRepository.deleteById(id);
    }
}