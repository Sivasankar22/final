package com.example.resume.resumeproject.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String LastName;

    private Long mobile;

    private String email;

    private Integer experience;

    private Integer relevantExperience;

    private String qualification;


    private String currentJobTitle;
    private String currentWorkLocation;
    private String city;

    private Integer zipCode;
    private String[] skillSet;


}