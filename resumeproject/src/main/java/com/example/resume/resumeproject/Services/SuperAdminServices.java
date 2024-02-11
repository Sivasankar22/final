package com.example.resume.resumeproject.Services;


import com.example.resume.resumeproject.Entities.Admin;
import com.example.resume.resumeproject.Entities.AuditAdminRecords;
import com.example.resume.resumeproject.Entities.SuperAdmin;
import com.example.resume.resumeproject.Repository.AdminRepository;
import com.example.resume.resumeproject.Repository.AuditAdminRecordsRepository;
import com.example.resume.resumeproject.Repository.SuperAdminRepository;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.Transformers.AdminTransforms;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
public class SuperAdminServices {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AuditAdminRecordsRepository auditAdminRecordsRepository;

    @Autowired
    SuperAdminRepository superAdminRepository;

    public String addSuperAdmin(SuperAdmin superAdmin) {
        superAdminRepository.save(superAdmin);
        return "superAdmin Registration Sucessfull";
    }

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

    public String DeleteAdmin(String adminName) {


        Optional<Admin> optionalNewadmin = Optional.ofNullable(adminRepository.findAdminByAdminName(adminName));

        Admin admin = optionalNewadmin.get();

        adminRepository.delete(admin);


        return "Admin has been Deleted";

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
}