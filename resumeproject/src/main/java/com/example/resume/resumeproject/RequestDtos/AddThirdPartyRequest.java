package com.example.resume.resumeproject.RequestDtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AddThirdPartyRequest {

    private  String organization;

    private  Long mobile;

    private  String email;

}
