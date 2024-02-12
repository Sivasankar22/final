package com.example.resume.resumeproject.Controller;

import com.example.resume.resumeproject.Entities.Applicant;
import com.example.resume.resumeproject.Entities.SuperAdmin;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.RequestDtos.AddJobRequest;
import com.example.resume.resumeproject.Services.AdminServices;
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
public class SuperAdminController {
    @Autowired
    AdminServices adminServices;

    @Autowired
    JobsServices jobsServices;

    @Autowired
    SuperAdminServices superAdminServices;

    @PostMapping("AddAdmin")
    public ResponseEntity<String> addAuthor(@RequestBody AddAdminRequest addAdminRequest) {

        try {
            log.info("We have request : {}", addAdminRequest.toString());
            String result = adminServices.addAdmin(addAdminRequest);
            return new ResponseEntity(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//    @PostMapping("/AdminRegistration")
//    public ResponseEntity<String> addAdmin(@RequestBody SuperAdmin superAdmin){
//
//        String result = superAdminServices.addSuperAdmin(superAdmin);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//
//    }
    @PutMapping("/Approval")
    public ResponseEntity<String> Approval(@RequestParam("AdminName") String AdminName) throws Exception {

        String result = superAdminServices.approveAdmin(AdminName);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @PutMapping ("/DeactiveAdmin")
    public ResponseEntity<String> DeactiveAdmin(@RequestParam("AdminName") String AdminName) {

        String result = superAdminServices.deactivateAdmin(AdminName);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @DeleteMapping("/DeletedAdmin")
    public ResponseEntity<String> DeletedAdmin(@RequestParam("AdminName") String AdminName){

        String result = superAdminServices.deleteAdmin(AdminName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/NewJob")
    public ResponseEntity<String> addJob(@RequestBody AddJobRequest addJobRequest, @RequestParam("SuperAdminName") String SuperAdminName) {

        try{
            log.info("We have request : {}",addJobRequest.toString());
            String result = jobsServices.addJobBYSuperAdmin(addJobRequest,SuperAdminName);
            return new ResponseEntity(result, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/ApprovalToApplicantBySuperAdmin")
    public ResponseEntity<String> ApplicantApproval(@RequestParam("SuperAdminName") String SuperAdminName,@RequestParam("ApplicantEmail") String ApplicantEmail) throws Exception {

        String result = superAdminServices.approveApplicantBySuperAdmin(SuperAdminName,ApplicantEmail);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @PutMapping("/ApprovalToThirdPartyBySuperAdmin")
    public ResponseEntity<String> ThirdpartyApproval(@RequestParam("SuperAdminName") String SuperAdminName,@RequestParam("ThirdPartyName") String ThirdPartyName) throws Exception {

        String result = superAdminServices.approveThirdPartyBySuperAdmin(SuperAdminName,ThirdPartyName);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping("/AssignJobToAdmin")
    public ResponseEntity<String> AssignJobToAdmin(@RequestParam("SuperAdminName") String SuperAdminName,@RequestParam("AdminName") String AdminName,@RequestBody AddJobRequest addJobRequest) throws Exception {

        String result = superAdminServices.assignJobToAdmin(SuperAdminName,AdminName,addJobRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @PutMapping("/AssignJobToThirdParty")
    public ResponseEntity<String> AssignJobToThirdParty(@RequestParam("SuperAdminName") String SuperAdminName,@RequestParam("ThirdPartyName") String ThirdPartyName,@RequestBody AddJobRequest addJobRequest) throws Exception {

        String result = superAdminServices.assignJobToThirdParty(SuperAdminName,ThirdPartyName,addJobRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/Applicantsort-by-experience")
    public ResponseEntity<List<Applicant>> getAllUsersSortedByExperience() {
        List<Applicant> users = superAdminServices.getAllUsersSortedByExperience();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/Applicantsort-alphabetically")
    public ResponseEntity<List<Applicant>> getAllUsersSortedAlphabetically() {
        List<Applicant> users = superAdminServices.getAllUsersSortedAlphabetically();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/Applicantsearch-BasedOnJobTitle")
    public ResponseEntity<List<Applicant>> getAllUsersBasedOnJobTitle(@RequestParam("JobTitle") String JobTitle) {
        List<Applicant> users = superAdminServices.getAllusersBasedJobTitle(JobTitle);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/Applicantsearch-BasedOnName")
    public ResponseEntity<List<Applicant>> getAlluserBasedOnName(@RequestParam("Name") String Name) {
        List<Applicant> users = superAdminServices.getAlluserBasedNames(Name);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/Applicantsearch-BasedOnExperience")
    public ResponseEntity<List<Applicant>> getAllUsersBasedOnExperience(@RequestParam("Experience") Integer Experience) {
        List<Applicant> users = superAdminServices.getAllUsersBasedOnExperience(Experience);
        return ResponseEntity.ok(users);
    }






}
