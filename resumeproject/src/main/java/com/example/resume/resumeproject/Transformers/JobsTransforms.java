package com.example.resume.resumeproject.Transformers;

import com.example.resume.resumeproject.Entities.Admin;
import com.example.resume.resumeproject.Entities.Jobs;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.RequestDtos.AddJobRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


public class JobsTransforms {

    public static Jobs convertAddJobReqToJob(AddJobRequest addJobRequest) {


        Jobs jobs = Jobs.builder().createdOn(addJobRequest.getCreatedOn())
                .category(addJobRequest.getCategory())
                .role(addJobRequest.getRole())
                .jobDescription(addJobRequest.getJobDescription())
                .salary(addJobRequest.getSalary())
                .skills(addJobRequest.getSkills())
                .noOfOpenings(addJobRequest.getNoOfOpenings())
                .totalNoOfExperience(addJobRequest.getTotalNoOfExperience())
                .relevantExperience(addJobRequest.getRelevantExperience())
                .build();


        return jobs;
    }
}




