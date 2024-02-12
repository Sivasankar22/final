package com.example.resume.resumeproject.Entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdParty {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    private  String organization;

    private  Long mobile;

    private  String email;

    private  Boolean authorized;


    @OneToMany( mappedBy = "thirdParty",cascade = CascadeType.ALL)
    private List<Jobs> jobsList = new ArrayList<>();

    @OneToMany ( mappedBy = "thirdParty",cascade = CascadeType.ALL)
    private List<Applicant> applicantList = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    private  Admin admin;

    @ManyToOne
    @JoinColumn
    private  SuperAdmin superAdmin;


}
