package com.example.resume.resumeproject.Entities;

import com.example.resume.resumeproject.RequestDtos.AddJobRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Admins")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String adminName;
    private String email;
    private String password;
    private  String organization;
    private  Boolean authorized=false;

    @ManyToOne
    @JoinColumn
    private  SuperAdmin superAdmin;

    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
    private List<ThirdParty>  thirdPartyList = new ArrayList<>();

    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
    private List<Jobs>  jobsList = new ArrayList<>();

    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
    private List<Applicant>  applicantList = new ArrayList<>();



}
