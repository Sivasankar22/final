package com.example.resume.resumeproject.Services;


import com.example.resume.resumeproject.Entities.*;
import com.example.resume.resumeproject.Repository.AdminRepository;
import com.example.resume.resumeproject.Repository.ApplicantRepository;
import com.example.resume.resumeproject.Repository.LockedAccountRepository;
import com.example.resume.resumeproject.Repository.ThirdPartyRepository;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.Transformers.AdminTransforms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class AdminServices {
    private Map<String, Integer> loginAttempts = new HashMap<>();
    private String  OTP;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private LockedAccountRepository lockedAccountRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private JavaMailSender mailSender;


    public String addAdmin(AddAdminRequest addAdminRequest) throws Exception {


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


    public String login(String email, String password) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findAdminByEmail(email));

            if (optionalAdmin.isPresent()) {
                Admin admin = optionalAdmin.get();
                if (!admin.getEmail().equals(email) || !admin.getPassword().equals(password)) {
                    int attempts = loginAttempts.getOrDefault(email, 0) + 1;
                    loginAttempts.put(email, attempts);
                    int remainingAttempts = 5 - attempts;
                    if (remainingAttempts <= 0) {
                        AccountLockPolicyAdmins lockedAccounts = new AccountLockPolicyAdmins();
                        lockedAccounts.setEmail(admin.getEmail());
                        lockedAccounts.setAuthorized(false);
                        lockedAccounts.setOrganization(admin.getOrganization());
                        lockedAccounts.setAdminName(admin.getAdminName());
                        lockedAccounts.setPassword(admin.getPassword());
                        lockedAccountRepository.save(lockedAccounts);
                        adminRepository.delete(admin);
                        return "Invalid email or password. Account locked. Please go to Account Lockout Policy.";
                    }
                    return "Invalid email or password. Please try again. Remaining attempts: " + remainingAttempts;
                } else {
                    // Reset login attempts on successful login
                    loginAttempts.remove(email);
                    return "Login successful.";
                }
            } else {
                return "Admin with email " + email + " not found.";
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing login: {}", e.getMessage());
            return "Failed to process login. Please try again later.";
        }
    }
    public String updateInformation(String adminName, AddAdminRequest addAdminRequest) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            // Convert the AddAdminRequest to an Admin object
            Admin admin = AdminTransforms.convertAddAdminReqToAdmin(addAdminRequest);

            // Find the existing admin by name
            Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findAdminByAdminName(adminName));

            if (optionalAdmin.isPresent()) {
                Admin existingAdmin = optionalAdmin.get();

                // Check if the existing admin account is active
                if (existingAdmin.getAuthorized()) {
                    // Update the existing admin with the new information
                    existingAdmin.setAdminName(admin.getAdminName());
                    existingAdmin.setEmail(admin.getEmail());
                    existingAdmin.setOrganization(admin.getOrganization());
                    existingAdmin.setPassword(admin.getPassword());

                    // Validate admin information
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

                    // Save the updated admin
                    adminRepository.save(existingAdmin);
                    return "Updated Successfully";
                } else {
                    return "Your account is still inactive and cannot be updated.";
                }
            } else {
                return "Admin with name " + adminName + " not found.";
            }
        } catch (Exception e) {
            logger.error("An error occurred while updating admin information: {}", e.getMessage());
            return "Failed to update admin information. Please try again later.";
        }
    }
    public String sendOTP(String Email)
    {
        OTP = generateOTP();
        AccountLockPolicyAdmins lockedAccounts= new AccountLockPolicyAdmins();
        lockedAccounts=lockedAccountRepository.findAdminByEmail(Email);

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String body = "Hi "+ lockedAccounts.getAdminName()+" !" +
                "Your 6 Digit otp have been registered. You can verify in next 60 secondst."+"Your OTP: "+OTP;

        mailMessage.setFrom("sivas2654@gmail.com");
        mailMessage.setTo(lockedAccounts.getEmail());
        mailMessage.setSubject("OTP VERIFICATION !!");
        mailMessage.setText(body);

        mailSender.send(mailMessage);
        return "otp have been registered";
    }
    public String VerifyOTP(String otp,String Email)
    {

        AccountLockPolicyAdmins lockedAccounts= new AccountLockPolicyAdmins();
        lockedAccounts=lockedAccountRepository.findAdminByEmail(Email);

        if (OTP != null && OTP.equals(otp)) {
            String password= lockedAccounts.getPassword();
            Admin admin = new Admin();
            admin.setAdminName(lockedAccounts.getAdminName());
            admin.setEmail(lockedAccounts.getEmail());
            admin.setPassword(lockedAccounts.getPassword());
            admin.setOrganization(lockedAccounts.getOrganization());
            lockedAccountRepository.delete(lockedAccounts);

            return "OTP verified successfully."+"Your account is Recovered";
        } else {
            // OTP didn't match or expired
            return "Invalid OTP.";
        }
    }
    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a random number between 100000 and 999999
        return String.valueOf(otp);
    }
    public String approveApplicant(String adminName, String applicantEmail) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            // Retrieve admin and applicant, if they exist
            Admin admin = adminRepository.findAdminByAdminName(adminName);
            Applicant applicant = applicantRepository.findApplicantByEmail(applicantEmail);

            if (admin != null && applicant != null) {
                // Authorize the applicant and set admin
                applicant.setAuthorized(true);
                applicant.setAdmin(admin);
                applicantRepository.save(applicant);

                // Return success message
                return "Applicant: " + applicantEmail + " has been authorized by " + adminName;
            } else {
                // Handle case when admin or applicant is not found
                return "Admin with name " + adminName + " or applicant with email " + applicantEmail + " not found.";
            }
        } catch (Exception e) {
            // Log and return error message
            logger.error("An error occurred while authorizing the admin: {}", e.getMessage());
            return "Failed to authorize admin. Please try again later.";
        }
    }

    public String approveThirdPartyByAdmin(String AdminName, String ThirdPartyName) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            // Retrieve superadmin and applicant, if they exist

            Admin admin = adminRepository.findAdminByAdminName(AdminName);
            ThirdParty thirdParty = thirdPartyRepository.findByOrganization(ThirdPartyName);

            if (AdminName != null && thirdParty != null) {
                // Authorize the applicant and set admin
                thirdParty.setAuthorized(true);
                thirdParty.setAdmin(admin);
                thirdPartyRepository.save(thirdParty);

                // Return success message
                return "ThirdParty: " + ThirdPartyName + " has been authorized by " + AdminName;
            } else {
                // Handle case when Superadmin or ThirdParty is not found
                return "SuperAdmin with name " + AdminName + " or Thirdparty with name " + ThirdPartyName + " not found.";
            }
        } catch (Exception e) {
            // Log and return error message
            logger.error("An error occurred while authorizing the admin: {}", e.getMessage());
            return "Failed to authorize Admin. Please try again later.";
        }
    }
}

