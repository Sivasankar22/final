package com.example.resume.resumeproject.Services;


import com.example.resume.resumeproject.Entities.Admin;
import com.example.resume.resumeproject.Entities.Applicant;
import com.example.resume.resumeproject.Entities.Jobs;
import com.example.resume.resumeproject.Repository.ApplicantRepository;
import com.example.resume.resumeproject.Repository.JobsRepository;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.RequestDtos.AddApplicantRequest;
import com.example.resume.resumeproject.RequestDtos.AddJobRequest;
import com.example.resume.resumeproject.Transformers.AdminTransforms;
import com.example.resume.resumeproject.Transformers.ApplicantTranforms;
import com.example.resume.resumeproject.Transformers.JobsTransforms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ApplicantServices {

    @Autowired
    ApplicantRepository applicantRepository;

    @Autowired
    JobsRepository jobsRepository;
    public String addApplicant(AddApplicantRequest addApplicantRequestRequest) throws Exception {

        Applicant newUser = ApplicantTranforms.convertAddApplicantReqToApplicant(addApplicantRequestRequest);
        String validationResult = validateUserData(newUser);
        if (validationResult != null)
        {

            return validationResult;
        }

        // Proceed with adding the user to the database
        applicantRepository.save(newUser);
        return "User has been added to the DB";
    }
    private String validateUserData(Applicant newUser) {

        // Validate email
        if (!isValidEmail(newUser.getEmail())) {
            return "Invalid email format.";
        }

        // Validate phone number
        if (!isValidMobileNumber(String.valueOf(newUser.getMobile())))
        {
            return "Invalid phone number format.";
        }


        // Validate other specific data types
        if (!containsOnlyAlphabets(newUser.getFirstName())) {
            return "Invalid FirstName format.";
        }
        if (!containsOnlyAlphabets(newUser.getLastName())) {
            return "Invalid LastName format.";
        }
        if (!containsOnlyAlphabets(newUser.getCity())) {
            return "Invalid City name.";
        }
        if (!containsOnlyAlphabets(newUser.getCurrentWorkLocation())) {
            return "Invalid CurrentWorkLocation format.";
        }
        if (!containsOnlyAlphabets(newUser.getCurrentJobTitle())) {
            return "Invalid CurrentJobTitle format.";
        }
        if (!containsOnlyAlphabets(newUser.getQualification())) {
            return "Invalid Qualification format.";
        }
        // If all validations pass, return null
        return null;

    }
    private boolean isValidEmail(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private boolean isValidMobileNumber(String mobileNumber) {

        String numericMobileNumber = mobileNumber.replaceAll("[^0-9]", "");


        if (numericMobileNumber.length() != 10) {
            return false;
        }


        return true;
    }
    private boolean containsOnlyAlphabets(String str) {

        String regex = "^[a-zA-Z]+$";
        return str.matches(regex);
    }

//    public List<AddJobRequest> getAllRevelantJobs(String Keyword1, String Keyword2) {
//        List<AddJobRequest> jobList = new ArrayList<>();
//        for (Jobs job : jobsRepository.findAll()) {
//            boolean containsKeyword1 = false;
//            boolean containsKeyword2 = false;
//            for (String item : job.getSkills()) {
//                if (item.toLowerCase().contains(Keyword1.toLowerCase())) {
//                    containsKeyword1 = true;
//                }
//                if (item.toLowerCase().contains(Keyword2.toLowerCase())) {
//                    containsKeyword2 = true;
//                }
//                if (containsKeyword1 && containsKeyword2) {
//                    break;
//                }
//            }
//            if (containsKeyword1 && containsKeyword2) {
//                JobsTransforms.convertAddJobReqToJob(job);
//                jobList.add();
//            }
//        }
//        return jobList;
//    }
    public List<Jobs> getAllRecentJobs() {
        return jobsRepository.findAllByOrderByCreatedOnDesc();
    }


    public List<Jobs> getAllSameCategoryJobs(String category) {
        List<Jobs> jobList = new ArrayList<>();

        for (Jobs job : jobsRepository.findAll()) {
            if (job.getCategory().toLowerCase().equals(category.toLowerCase())) {
                jobList.add(job);
            }
        }

        return jobList;
    }

    public List<Jobs> getAllRevelantJobs(String Keyword1, String Keyword2) {
        List<Jobs> jobList = new ArrayList<>();
        for (Jobs job : jobsRepository.findAll()) {
            boolean containsKeyword1 = false;
            boolean containsKeyword2 = false;
            for (String item : job.getSkills()) {
                if (item.toLowerCase().contains(Keyword1.toLowerCase())) {
                    containsKeyword1 = true;
                }
                if (item.toLowerCase().contains(Keyword2.toLowerCase())) {
                    containsKeyword2 = true;
                }
                if (containsKeyword1 && containsKeyword2) {
                    break;
                }
            }
            if (containsKeyword1 && containsKeyword2) {
                jobList.add(job);
            }
        }
        return jobList;
    }

    public String editDetails(String email, Applicant updatedApplicant) {
        Optional<Applicant> optionalApplicant = Optional.ofNullable(applicantRepository.findApplicantByEmail(email));

        if (optionalApplicant.isPresent()) {
            Applicant existingApplicant = optionalApplicant.get();

            if (existingApplicant.isAuthorized()) {
                // Update the existing applicant's details
                updateApplicantDetails(existingApplicant, updatedApplicant);
                applicantRepository.save(existingApplicant);

                return existingApplicant.getFirstName() + " has been updated.";
            } else {
                return "User is not authorized to update details.";
            }
        } else {
            return "User not found.";
        }
    }

    private void updateApplicantDetails(Applicant existingApplicant, Applicant updatedApplicant) {
        existingApplicant.setFirstName(updatedApplicant.getFirstName());
        existingApplicant.setLastName(updatedApplicant.getLastName());
        existingApplicant.setMobile(updatedApplicant.getMobile());
        existingApplicant.setEmail(updatedApplicant.getEmail());
        existingApplicant.setExperience(updatedApplicant.getExperience());
        existingApplicant.setRelevantExperience(updatedApplicant.getRelevantExperience());
        existingApplicant.setCurrentJobTitle(updatedApplicant.getCurrentJobTitle());
        existingApplicant.setCurrentWorkLocation(updatedApplicant.getCurrentWorkLocation());
        existingApplicant.setCity(updatedApplicant.getCity());
        existingApplicant.setZipCode(updatedApplicant.getZipCode());
        existingApplicant.setSkillSet(updatedApplicant.getSkillSet());
    }

    public String deleteApplicant(String email) {
        Optional<Applicant> optionalApplicant = Optional.ofNullable(applicantRepository.findApplicantByEmail(email));

        if (optionalApplicant.isPresent()) {
            Applicant existingApplicant = optionalApplicant.get();

            if (existingApplicant.isAuthorized()) {
                applicantRepository.delete(existingApplicant);

                return existingApplicant.getEmail() + " has been Deleted.";
            } else {
                return "User is not authorized to delete details.";
            }
        } else {
            return "User not found.";
        }
    }
    public Object getApplicantDetails(String email) {
        Optional<Applicant> optionalApplicant = Optional.ofNullable(applicantRepository.findApplicantByEmail(email));

        if (optionalApplicant.isPresent()) {
            Applicant applicant = optionalApplicant.get();

            if (applicant.isAuthorized()) {
                return applicant;
            } else {
                return "User is not authorized to access details.";
            }
        } else {
            return "User not found.";
        }
    }




}

