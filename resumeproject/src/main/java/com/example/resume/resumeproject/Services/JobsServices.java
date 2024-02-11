package com.example.resume.resumeproject.Services;


import com.example.resume.resumeproject.Entities.Admin;
import com.example.resume.resumeproject.Entities.Jobs;
import com.example.resume.resumeproject.Entities.SuperAdmin;
import com.example.resume.resumeproject.Repository.AdminRepository;
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

@Service
public class JobsServices {

    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    SuperAdminRepository superAdminRepository;

    @Autowired
    AdminRepository adminRepository;

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

}
