package com.example.resume.resumeproject.RequestDtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AddAdminRequest {

    private String adminName;
    private String Email;
    private String password;
    private  String Organization;
}
