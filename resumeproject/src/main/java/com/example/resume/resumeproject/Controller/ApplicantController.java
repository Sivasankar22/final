package com.example.resume.resumeproject.Controller;


import com.example.resume.resumeproject.Entities.Applicant;
import com.example.resume.resumeproject.Entities.Jobs;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.RequestDtos.AddApplicantRequest;
import com.example.resume.resumeproject.RequestDtos.AddJobRequest;
import com.example.resume.resumeproject.Services.ApplicantServices;
import com.example.resume.resumeproject.Services.JobsServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ApplicantController {

    @Autowired
    ApplicantServices applicantServices;

    @Autowired
    JobsServices jobsServices;

    @PostMapping("Application")
    public ResponseEntity<String> addAuthor(@RequestBody AddApplicantRequest addApplicantRequest){

        try{
            log.info("We have request : {}",addApplicantRequest.toString());
            String result = applicantServices.addApplicant(addApplicantRequest);
            return new ResponseEntity(result, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/search-RevelantJobs")
    public ResponseEntity<List<Jobs>> getAllRevelantJobs(@RequestParam("keyword1") String Keyword1, @RequestParam("keyword2") String Keyword2) {
        List<Jobs>  jobsList = applicantServices.getAllRevelantJobs(Keyword1,Keyword2);
        return ResponseEntity.ok(jobsList);
    }
    @GetMapping("/search-Recent")
    public ResponseEntity<List<Jobs>> getAllRecentJobs() {
        List<Jobs>  jobsList = applicantServices.getAllRecentJobs();
        return ResponseEntity.ok(jobsList);
    }
    @GetMapping("/search-Category")
    public ResponseEntity<List<Jobs>> getAllSameCategoryJobs(@RequestParam("Category") String Category) {
        List<Jobs>  jobsList = applicantServices.getAllSameCategoryJobs(Category);
        return ResponseEntity.ok(jobsList);
    }
    @GetMapping("/view")
    public ResponseEntity<Object> viewUser(@RequestParam("Email") String email) {
        Object result = applicantServices.getApplicantDetails(email);
        if (result instanceof Applicant) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/edit")
    public ResponseEntity<String> edituser(@RequestParam("Email") String Email, @RequestBody Applicant applicant) {
        String result = applicantServices.editDetails(Email, applicant);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteuser(@RequestParam("Email") String Email) {
        String result = applicantServices.deleteApplicant(Email);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
