package com.example.resume.resumeproject.RequestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AddJobRequest {

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
}
