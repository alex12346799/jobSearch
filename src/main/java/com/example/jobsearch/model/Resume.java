package com.example.jobsearch.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    @OneToMany(mappedBy = "resume")
    private List<RespondentApplicant> respondentApplicant;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private double salary;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "resume")
    private List<EducationInfo> educationInfoList;

    @OneToMany(mappedBy = "resume")
    private List<WorkExperienceInfo> workExperienceInfoList;

    @OneToOne(mappedBy = "resume")
    private SocialLinks socialLinks;


}
