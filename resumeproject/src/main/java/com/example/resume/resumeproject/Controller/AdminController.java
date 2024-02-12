package com.example.resume.resumeproject.Controller;


import com.example.resume.resumeproject.Entities.Admin;
import com.example.resume.resumeproject.Entities.Applicant;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.RequestDtos.AddJobRequest;
import com.example.resume.resumeproject.Services.AdminServices;
import com.example.resume.resumeproject.Services.ApplicantServices;
import com.example.resume.resumeproject.Services.JobsServices;
import com.example.resume.resumeproject.Services.SuperAdminServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
public class AdminController {
    @Autowired
    AdminServices adminServices;


    @Autowired
    JobsServices jobsServices;
    @Autowired
    ApplicantServices applicantServices;
    @PostMapping("Registration")
    public ResponseEntity<String> addAdmin(@RequestBody AddAdminRequest addAdminRequest){

        try{
            log.info("We have request : {}",addAdminRequest.toString());
            String result = adminServices.addAdmin(addAdminRequest);
            return new ResponseEntity(result, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/SendOTP")
    public ResponseEntity<String> SendOTP(@RequestParam("Email") String Email){

        String result = adminServices.sendOTP(Email);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @PostMapping("/VerifyOTP")
    public ResponseEntity<String>  VerifyOTP(@RequestParam("OTP") String OTP,@RequestParam("Email") String Email){

        String result = adminServices.VerifyOTP(OTP, Email);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @GetMapping("/Login")
    public ResponseEntity<String> addAuthor(@RequestParam("Email") String Email,@RequestParam("Password") String Password){

        String result = adminServices.login(Email,Password);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @PutMapping("/UpdateInformation")
    public  ResponseEntity<String> updateInformation(@RequestParam("AdminName") String  AdminName,@RequestBody AddAdminRequest addAdminRequest)
    {
        String result = adminServices.updateInformation(AdminName,addAdminRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/Job")
    public ResponseEntity<String> addJob(@RequestBody AddJobRequest addJobRequest,@RequestParam("AdminName") String AdminName) {

        try{
            log.info("We have request : {}",addJobRequest.toString());
            String result = jobsServices.addJobByAdmin(addJobRequest,AdminName);
            return new ResponseEntity(result, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/ApprovalToApplicantByAdmin")
    public ResponseEntity<String> ApplicantApproval(@RequestParam("AdminName") String AdminName,@RequestParam("ApplicantEmail") String ApplicantEmail) throws Exception {

        String result = adminServices.approveApplicant(AdminName,ApplicantEmail);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @PutMapping("/ApprovalToThirdPartyByAdmin")
    public ResponseEntity<String> ThirdpartyApproval(@RequestParam("AdminName") String AdminName,@RequestParam("ThirdPartyName") String ThirdPartyName) throws Exception {

        String result = adminServices.approveThirdPartyByAdmin(AdminName,ThirdPartyName);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }


}




