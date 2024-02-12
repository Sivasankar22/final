package com.example.resume.resumeproject.Transformers;


import com.example.resume.resumeproject.Entities.Admin;
import com.example.resume.resumeproject.Entities.Applicant;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.RequestDtos.AddApplicantRequest;

public class ApplicantTranforms {

    public static Applicant convertAddApplicantReqToApplicant(AddApplicantRequest addAdminRequest){

        Applicant applicant = Applicant.builder().firstName(addAdminRequest.getFirstName())
                .LastName(addAdminRequest.getLastName())
                .email(addAdminRequest.getEmail())
                .city(addAdminRequest.getCity())
                .currentJobTitle(addAdminRequest.getCurrentJobTitle())
                .currentWorkLocation(addAdminRequest.getCurrentWorkLocation())
                .experience(addAdminRequest.getExperience())
                .relevantExperience(addAdminRequest.getRelevantExperience())
                .skillSet(addAdminRequest.getSkillSet())
                .mobile(addAdminRequest.getMobile())
                .qualification(addAdminRequest.getQualification())
                .zipCode(addAdminRequest.getZipCode()).build();

        return applicant;
    }
}
