package com.io.github.cawodevelopment.jobtracker.jobapplication;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "job_applications")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 2048)
    @Column(name = "job_url")
    private String jobUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "job_type")
    private JobType jobType;

    @NotBlank
    @Size(max = 255)
    private String location;

    @PositiveOrZero
    @Column(name = "salary_min")
    private int salaryMin;

    @PositiveOrZero
    @Column(name = "salary_max")
    private int salaryMax;

    @PastOrPresent
    @Column(name = "date_applied")
    private LocalDate dateApplied;

    @FutureOrPresent
    private LocalDate deadline;

    @NotNull
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @Size(max = 5000)
    private String notes;
}
