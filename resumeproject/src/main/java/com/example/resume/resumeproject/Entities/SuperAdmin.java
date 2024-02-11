package com.example.resume.resumeproject.Entities;



import jakarta.persistence.*;
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
public class SuperAdmin {
    @Id

    private  Integer id;

    private  String superAdminName;




    @OneToMany(mappedBy = "superAdmin",cascade = CascadeType.ALL)
    private List<Admin> adminList = new ArrayList<>();

    @OneToMany(mappedBy = "superAdmin",cascade = CascadeType.ALL)
    private List<Jobs> jobsList = new ArrayList<>();





}

