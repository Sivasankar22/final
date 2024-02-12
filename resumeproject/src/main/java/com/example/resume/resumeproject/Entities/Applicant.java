package com.example.resume.resumeproject.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
    private String firstName;
    private String LastName;

    private Long mobile;
    @Column(unique = true)
    private String email;

    private Integer experience;

    private Integer relevantExperience;

    private String qualification;


    private String currentJobTitle;
    private String currentWorkLocation;
    private String city;

    private Integer zipCode;
    private String[] skillSet;

    private  boolean authorized=false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
   private ThirdParty thirdParty;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private SuperAdmin superAdmin;
    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private  Admin admin;


}