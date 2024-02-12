package com.example.resume.resumeproject.RequestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AddApplicantRequest {
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
