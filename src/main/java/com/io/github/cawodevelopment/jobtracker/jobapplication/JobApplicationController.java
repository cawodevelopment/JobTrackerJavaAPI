package com.io.github.cawodevelopment.jobtracker.jobapplication;

import com.io.github.cawodevelopment.jobtracker.jobapplication.dto.JobApplicationRequest;
import com.io.github.cawodevelopment.jobtracker.jobapplication.dto.JobApplicationResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    public ResponseEntity<Page<JobApplicationResponse>> getJobApplications(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobApplicationService.getJobApplications(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> getJobApplicationById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobApplicationService.getJobApplicationById(id));
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponse> createJobApplication(@Valid @RequestBody JobApplicationRequest request) {
        JobApplicationResponse response = jobApplicationService.createJobApplication(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> updateJobApplication(@PathVariable Long id, @Valid @RequestBody JobApplicationRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobApplicationService.updateJobApplicationById(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable Long id) {
        jobApplicationService.deleteJobApplicationById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}