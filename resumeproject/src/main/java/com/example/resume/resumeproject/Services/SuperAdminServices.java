package com.example.resume.resumeproject.Services;


import com.example.resume.resumeproject.Entities.*;
import com.example.resume.resumeproject.Repository.*;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.RequestDtos.AddJobRequest;
import com.example.resume.resumeproject.Transformers.AdminTransforms;
import com.example.resume.resumeproject.Transformers.JobsTransforms;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
public class SuperAdminServices {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ApplicantRepository applicantRepository;

    @Autowired
    AuditAdminRecordsRepository auditAdminRecordsRepository;

    @Autowired
    SuperAdminRepository superAdminRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

//    public String addSuperAdmin(SuperAdmin superAdmin) {
//        superAdminRepository.save(superAdmin);
//        return "superAdmin Registration Sucessfull";
//    }

    public  List<AddJobRequest> jobsList = new ArrayList<>();
    public String addAdminBySuperAdmin(AddAdminRequest addAdminRequest) throws Exception {


        Admin admin = AdminTransforms.convertAddAdminReqToAdmin(addAdminRequest);
        if (!isValidName(admin.getAdminName())) {
            return "Invalid name format. Name should contain only alphabetic characters.";
        }
        if (!isValidName(admin.getOrganization())) {
            return "Invalid Organization format. Name should contain only alphabetic characters.";
        }

        if (!isValidPassword(admin.getPassword())) {
            return "Invalid password format. Password should have at least one small alphabet, one big alphabet, one special character, one digit with length=8.";
        }

        if (!isValidEmail(admin.getEmail())) {
            return "Invalid email format.";
        }

        adminRepository.save(admin);

        return "Admin has been added to the Database successfully";

    }

    private boolean isValidName(String name) {
        return Pattern.matches("^[a-zA-Z]+$", name);
    }

    private boolean isValidPassword(String password) {
        // Password should contain one small alphabet, one big alphabet, one special character, one digit.
        return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=.*[a-zA-Z0-9@#$%^&+=]).{8,}$", password);
    }

    private boolean isValidEmail(String email) {
        // Email format validation using a simple regex
        return Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email);
    }

    public String approveAdmin(String adminName) throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findAdminByAdminName(adminName));

            if (optionalAdmin.isPresent()) {
                Admin admin = optionalAdmin.get();
                admin.setAuthorized(true);
                adminRepository.save(admin);
                return "Admin " + adminName + " has been authorized.";
            } else {
                return "Admin with name " + adminName + " not found.";
            }
        } catch (Exception e) {
            logger.error("An error occurred while authorizing the admin: {}", e.getMessage());
            return "Failed to authorize admin. Please try again later.";
        }
    }


    public String deactivateAdmin(String adminName) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findAdminByAdminName(adminName));

            if (optionalAdmin.isPresent()) {
                Admin admin = optionalAdmin.get();

                // Create audit record before deactivating admin
                AuditAdminRecords auditRecords = new AuditAdminRecords();
                auditRecords.setAdminName(admin.getAdminName());
                auditRecords.setEmail(admin.getEmail());
                auditRecords.setOrganization(admin.getOrganization());
                auditRecords.setPassword(admin.getPassword());
                auditAdminRecordsRepository.save(auditRecords);

                // Deactivate admin
                admin.setAuthorized(false);
                adminRepository.save(admin);

                // Delete admin
                adminRepository.delete(admin);

                return "Admin account has been deactivated.";
            } else {
                return "Admin with name " + adminName + " not found.";
            }
        } catch (Exception e) {
            logger.error("An error occurred while deactivating the admin: {}", e.getMessage());
            return "Failed to deactivate admin. Please try again later.";
        }
    }


    public String deleteAdmin(String adminName) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findAdminByAdminName(adminName));

            if (optionalAdmin.isPresent()) {
                Admin admin = optionalAdmin.get();
                adminRepository.delete(admin);
                return "Admin " + adminName + " has been deleted.";
            } else {
                return "Admin with name " + adminName + " not found.";
            }
        } catch (Exception e) {
            logger.error("An error occurred while deleting the admin: {}", e.getMessage());
            return "Failed to delete admin. Please try again later.";
        }
}
    public String approveApplicantBySuperAdmin(String SuperadminName, String applicantEmail) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            // Retrieve superadmin and applicant, if they exist

            SuperAdmin superAdmin = superAdminRepository.findBySuperAdminName(SuperadminName);
            Applicant applicant = applicantRepository.findApplicantByEmail(applicantEmail);

            if (superAdmin != null && applicant != null) {
                // Authorize the applicant and set admin
                applicant.setAuthorized(true);
                applicant.setSuperAdmin(superAdmin);
                applicantRepository.save(applicant);

                // Return success message
                return "Applicant: " + applicantEmail + " has been authorized by " + SuperadminName;
            } else {
                // Handle case when admin or applicant is not found
                return "SuperAdmin with name " + SuperadminName + " or applicant with email " + applicantEmail + " not found.";
            }
        } catch (Exception e) {
            // Log and return error message
            logger.error("An error occurred while authorizing the admin: {}", e.getMessage());
            return "Failed to authorize Superadmin. Please try again later.";
        }
    }
    public String approveThirdPartyBySuperAdmin(String SuperadminName, String ThirdPartyName) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            // Retrieve superadmin and applicant, if they exist

            SuperAdmin superAdmin = superAdminRepository.findBySuperAdminName(SuperadminName);
            ThirdParty thirdParty = thirdPartyRepository.findByOrganization(ThirdPartyName);

            if (superAdmin != null && thirdParty != null) {
                // Authorize the applicant and set admin
                thirdParty.setAuthorized(true);
                thirdParty.setSuperAdmin(superAdmin);
                thirdPartyRepository.save(thirdParty);

                // Return success message
                return "ThirdParty: " + ThirdPartyName + " has been authorized by " + SuperadminName;
            } else {
                // Handle case when Superadmin or ThirdParty is not found
                return "SuperAdmin with name " + SuperadminName + " or Thirdparty with name " + ThirdPartyName + " not found.";
            }
        } catch (Exception e) {
            // Log and return error message
            logger.error("An error occurred while authorizing the admin: {}", e.getMessage());
            return "Failed to authorize Superadmin. Please try again later.";
        }
    }


    public String assignJobToAdmin(String superAdminName, String adminName, AddJobRequest addJobRequest) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            // Retrieve super admin and admin, if they exist
            SuperAdmin superAdmin = superAdminRepository.findBySuperAdminName(superAdminName);
            Admin admin = adminRepository.findAdminByAdminName(adminName);

            if (superAdmin != null && admin != null) {
                // Assign job to admin
                Jobs job = JobsTransforms.convertAddJobReqToJob(addJobRequest);
                job.setSuperAdmin(superAdmin);
                admin.getJobsList().add(job);
                adminRepository.save(admin);

                // Return success message
                return "Job has been assigned to Admin: " + adminName + " by Super Admin: " + superAdminName;
            } else {
                // Handle case when Super Admin or Admin is not found
                return "Super Admin with name " + superAdminName + " or Admin with name " + adminName + " not found.";
            }
        } catch (Exception e) {
            // Log and return error message
            logger.error("An error occurred while assigning the job to admin: {}", e.getMessage());
            return "Failed to assign job to admin. Please try again later.";
        }
    }

    public String assignJobToThirdParty(String superAdminName, String thirdPartyName, AddJobRequest addJobRequest) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            // Retrieve super admin and third party, if they exist
            SuperAdmin superAdmin = superAdminRepository.findBySuperAdminName(superAdminName);
            ThirdParty thirdParty = thirdPartyRepository.findByOrganization(thirdPartyName);

            if (superAdmin != null && thirdParty != null) {
                // Assign job to third party
                Jobs job = JobsTransforms.convertAddJobReqToJob(addJobRequest);
                job.setThirdParty(thirdParty);
                thirdParty.getJobsList().add(job);
                thirdPartyRepository.save(thirdParty);

                // Return success message
                return "Job has been assigned to Third Party: " + thirdPartyName + " by Super Admin: " + superAdminName;
            } else {
                // Handle case when Super Admin or Third Party is not found
                return "Super Admin with name " + superAdminName + " or Third Party with name " + thirdPartyName + " not found.";
            }
        } catch (Exception e) {
            // Log and return error message
            logger.error("An error occurred while assigning the job to third party: {}", e.getMessage());
            return "Failed to assign job to third party. Please try again later.";
        }
    }


    public List<Applicant> getAllUsersSortedAlphabetically() {
        return applicantRepository.findAllByOrderByFirstNameAsc();
    }

    public List<Applicant> getAllUsersSortedByExperience() {
        return applicantRepository.findAllByOrderByExperienceDesc();
    }

    public List<Applicant> getAllusersBasedJobTitle(String JobTitle) {
        List<Applicant> userList = new ArrayList<>();
        String str1 = removeSpaces(JobTitle);

        for (Applicant newUser : applicantRepository.findAll()) {
            String str2 = removeSpaces(newUser.getCurrentJobTitle());
            int comparisonResult = str1.compareToIgnoreCase(str2);
            if (comparisonResult == 0) {
                userList.add(newUser);
            }
        }

        return userList;
    }

    public List<Applicant> getAlluserBasedNames(String Name) {
        List<Applicant> userList = new ArrayList<>();
        String str1 = removeSpaces(Name);

        for (Applicant newUser : applicantRepository.findAll()) {
            String str2 = removeSpaces(newUser.getFirstName());
            int comparisonResult = str1.compareToIgnoreCase(str2);
            if (comparisonResult == 0) {
                userList.add(newUser);
            }
        }

        return userList;
    }

    private static String removeSpaces(String str) {
        return str.replaceAll("\\s+", "");
    }

    public List<Applicant> getAllUsersBasedOnExperience(Integer Experience) {
        return applicantRepository.findAllByExperience(Experience);
    }
}