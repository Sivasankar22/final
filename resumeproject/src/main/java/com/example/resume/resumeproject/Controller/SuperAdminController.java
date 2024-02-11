package com.example.resume.resumeproject.Controller;

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
    @PostMapping("/AdminRegistration")
    public ResponseEntity<String> addAdmin(@RequestBody SuperAdmin superAdmin){

        String result = superAdminServices.addSuperAdmin(superAdmin);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
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

        String result = superAdminServices.DeleteAdmin(AdminName);
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



}
