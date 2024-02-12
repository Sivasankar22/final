package com.example.resume.resumeproject.Entities;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditAdminRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String adminName;


    private String email;


    private String password;


    private  String organization;

    private boolean authorized;

    @ManyToOne
    @JoinColumn
    private SuperAdmin Superadmin;


}
