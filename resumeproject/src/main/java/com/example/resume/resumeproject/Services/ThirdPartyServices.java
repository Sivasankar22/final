package com.example.resume.resumeproject.Services;


import com.example.resume.resumeproject.Entities.Admin;
import com.example.resume.resumeproject.Entities.ThirdParty;
import com.example.resume.resumeproject.Repository.ThirdPartyRepository;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.RequestDtos.AddThirdPartyRequest;
import com.example.resume.resumeproject.Transformers.AdminTransforms;
import com.example.resume.resumeproject.Transformers.ThirdPartyTransforms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ThirdPartyServices {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public String addThirdParty(AddThirdPartyRequest addThirdPartyRequest) throws Exception {


        ThirdParty thirdParty = ThirdPartyTransforms.convertAddThirdPartyReqToAddThirdParty(addThirdPartyRequest);
        if (!isValidName(thirdParty.getOrganization())) {
            return "Invalid name format. Name should contain only alphabetic characters.";
        }
        if (!isValidEmail(thirdParty.getEmail())) {
            return "Invalid email format.";
        }
        if (!isValidMobileNumber(String.valueOf(thirdParty.getMobile()))) {
            return "Invalid phone number format.";
        }

        thirdPartyRepository.save(thirdParty);

        return "Admin has been added to the Database successfully";
    }

    private boolean isValidName(String name) {
        return Pattern.matches("^[a-zA-Z]+$", name);
    }

    private boolean isValidEmail(String email) {
        // Email format validation using a simple regex
        return Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email);
    }

    private boolean isValidMobileNumber(String mobileNumber) {
        // Remove non-numeric characters from the mobile number string
        String numericMobileNumber = mobileNumber.replaceAll("[^0-9]", "");

        // Ensure that the numericMobileNumber contains exactly 10 digits
        if (numericMobileNumber.length() != 10) {
            return false;
        }
        return true;
    }
}
