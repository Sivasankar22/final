package com.example.resume.resumeproject.Controller;



import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;
import com.example.resume.resumeproject.RequestDtos.AddThirdPartyRequest;
import com.example.resume.resumeproject.Services.ThirdPartyServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ThirdPartyController {

    @Autowired
    ThirdPartyServices thirdPartyServices;

    @PostMapping("AddThirdParty")
    public ResponseEntity<String> addThirdParty(@RequestBody AddThirdPartyRequest addThirdPartyRequest){

        try{
            log.info("We have request : {}",addThirdPartyRequest.toString());
            String result = thirdPartyServices.addThirdParty(addThirdPartyRequest);
            return new ResponseEntity(result, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }



}


