package com.io.github.cawodevelopment.jobtracker.jobapplication;

import com.io.github.cawodevelopment.jobtracker.jobapplication.dto.JobApplicationRequest;
import com.io.github.cawodevelopment.jobtracker.jobapplication.dto.JobApplicationResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.io.github.cawodevelopment.jobtracker.auth.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    public ResponseEntity<Page<JobApplicationResponse>> getJobApplications(
            Authentication authentication,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobApplicationService.getJobApplications(userDetails.getId(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> getJobApplicationById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobApplicationService.getJobApplicationById(id, userDetails.getId()));
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponse> createJobApplication(
            @Valid @RequestBody JobApplicationRequest request,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        JobApplicationResponse response = jobApplicationService.createJobApplication(request, userDetails.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> updateJobApplication(
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequest request,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobApplicationService.updateJobApplicationById(request, id, userDetails.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobApplication(
            @PathVariable Long id,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        jobApplicationService.deleteJobApplicationById(id, userDetails.getId());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}