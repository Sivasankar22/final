package com.example.resume.resumeproject.Transformers;

import com.example.resume.resumeproject.Entities.Admin;
import com.example.resume.resumeproject.RequestDtos.AddAdminRequest;

public class AdminTransforms {

    public static Admin convertAddAdminReqToAdmin(AddAdminRequest addAdminRequest){
        Admin admin = Admin.builder().adminName(addAdminRequest.getAdminName())
                .email(addAdminRequest.getEmail())
                .password(addAdminRequest.getPassword())
                .organization(addAdminRequest.getOrganization())
                .build();



        return admin;
    }
}
