package com.io.github.cawodevelopment.jobtracker.jobapplication;

import com.io.github.cawodevelopment.jobtracker.jobapplication.dto.JobApplicationRequest;
import com.io.github.cawodevelopment.jobtracker.jobapplication.dto.JobApplicationResponse;
import com.io.github.cawodevelopment.jobtracker.user.User;
import com.io.github.cawodevelopment.jobtracker.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper jobApplicationMapper;
    private final UserRepository userRepository;

    public JobApplicationService(
            JobApplicationRepository jobApplicationRepository,
            JobApplicationMapper jobApplicationMapper,
            UserRepository userRepository
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobApplicationMapper = jobApplicationMapper;
        this.userRepository = userRepository;
    }

    public Page<JobApplicationResponse> getJobApplications(Long userId, Pageable pageable) {
        return jobApplicationRepository.findAllByUser_Id(userId, pageable)
                .map(jobApplication -> jobApplicationMapper
                        .toResponse(jobApplication));
    }

    public JobApplicationResponse getJobApplicationById(Long id, Long userId) {
        JobApplication jobApplication = jobApplicationRepository
                .findByIdAndUser_Id(id, userId)
                .orElseThrow();

        return jobApplicationMapper.toResponse(jobApplication);
    }

    public JobApplicationResponse createJobApplication(JobApplicationRequest request, Long userId) {
        JobApplication jobApplication = jobApplicationMapper.toEntity(request);
        User user = userRepository.findById(userId).orElseThrow();
        jobApplication.setUser(user);

        JobApplication savedJobApplication = jobApplicationRepository.save(jobApplication);

        return jobApplicationMapper.toResponse(savedJobApplication);
    }

    public JobApplicationResponse updateJobApplicationById(JobApplicationRequest request, Long id, Long userId) {
        JobApplication jobApplication = jobApplicationRepository
                .findByIdAndUser_Id(id, userId)
                .orElseThrow();

        jobApplicationMapper.updateEntity(jobApplication, request);

        JobApplication savedJobApplication = jobApplicationRepository.save(jobApplication);

        return jobApplicationMapper.toResponse(savedJobApplication);
    }

    public void deleteJobApplicationById(Long id, Long userId) {
        jobApplicationRepository.deleteByIdAndUser_Id(id, userId);
    }
}