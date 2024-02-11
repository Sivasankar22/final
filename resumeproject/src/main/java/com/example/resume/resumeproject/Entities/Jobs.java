package com.example.resume.resumeproject.Entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table
@Data
@Builder

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

    @ManyToOne
    @JoinColumn
    private Admin admin;

    @ManyToOne
    @JoinColumn
    private SuperAdmin superAdmin;





}

