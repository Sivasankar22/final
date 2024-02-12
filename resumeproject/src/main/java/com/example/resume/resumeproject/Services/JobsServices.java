package com.example.resume.resumeproject.Services;


import com.example.resume.resumeproject.Entities.Admin;
import com.example.resume.resumeproject.Entities.Applicant;
import com.example.resume.resumeproject.Entities.Jobs;
import com.example.resume.resumeproject.Entities.SuperAdmin;
import com.example.resume.resumeproject.Repository.AdminRepository;
import com.example.resume.resumeproject.Repository.ApplicantRepository;
import com.example.resume.resumeproject.Repository.JobsRepository;
import com.example.resume.resumeproject.Repository.SuperAdminRepository;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.RequestDtos.AddJobRequest;
import com.example.resume.resumeproject.Transformers.AdminTransforms;
import com.example.resume.resumeproject.Transformers.JobsTransforms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobsServices {

    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    SuperAdminRepository superAdminRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ApplicantRepository applicantRepository;




    public String addJobBYSuperAdmin(AddJobRequest addJobRequest,String superAdminName) throws Exception {


        SuperAdmin superAdmin = superAdminRepository.findBySuperAdminName(superAdminName);
        Jobs jobs = JobsTransforms.convertAddJobReqToJob(addJobRequest);
        jobs.setSuperAdmin(superAdmin);
        jobsRepository.save(jobs);

        return "Job has Posted";

    }
    public String addJobByAdmin(AddJobRequest addJobRequest, String adminName) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            Admin admin = adminRepository.findAdminByAdminName(adminName);
            if (admin != null && admin.getAuthorized()) {
                Jobs job = JobsTransforms.convertAddJobReqToJob(addJobRequest);
                job.setAdmin(admin);
                jobsRepository.save(job);
                return "Job has been posted successfully.";
            } else {
                return "You are not authorized to add jobs.";
            }
        } catch (Exception e) {
            logger.error("An error occurred while adding job: {}", e.getMessage());
            return "Failed to add job. Please try again later.";
        }
    }




//    public List<NewUser> getAllUsersSortedAlphabetically() {
//        return userRepository.findAllByOrderByFirstNameAsc();
//    }
//
//    public List<NewUser> getAllUsersSortedByExperience() {
//        return userRepository.findAllByOrderByExperienceDesc();
//    }
//
//    public List<NewUser> getAllusersBasedJobTitle(String JobTitle) {
//        List<NewUser> userList = new ArrayList<>();
//        String str1 = removeSpaces(JobTitle);
//
//        for (NewUser newUser : userRepository.findAll()) {
//            String str2 = removeSpaces(newUser.getCurrentJobTitle());
//            int comparisonResult = str1.compareToIgnoreCase(str2);
//            if (comparisonResult == 0) {
//                userList.add(newUser);
//            }
//        }
//
//        return userList;
//    }
//
//    public List<NewUser> getAlluserBasedNames(String Name) {
//        List<NewUser> userList = new ArrayList<>();
//        String str1 = removeSpaces(Name);
//
//        for (NewUser newUser : userRepository.findAll()) {
//            String str2 = removeSpaces(newUser.getFirstName());
//            int comparisonResult = str1.compareToIgnoreCase(str2);
//            if (comparisonResult == 0) {
//                userList.add(newUser);
//            }
//        }
//
//        return userList;
//    }
//
//    private static String removeSpaces(String str) {
//        return str.replaceAll("\\s+", "");
//    }
//
//    public List<NewUser> getAllUsersBasedOnExperience(Integer Experience) {
//        return userRepository.findAllByExperience(Experience);
//    }
//}

}
