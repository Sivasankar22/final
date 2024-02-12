package com.example.resume.resumeproject.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;


    @CreationTimestamp
    private Date createdOn;

    private  String role;

    private  String jobDescription;

    private  String[] skills;

    private  Integer noOfOpenings;

    private  Integer salary;

    private  String category;

    private  Integer totalNoOfExperience;

    private  Integer relevantExperience;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Admin admin;
    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private SuperAdmin superAdmin;
    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private  ThirdParty thirdParty;
    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private  Applicant applicant;





}

