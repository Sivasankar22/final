package com.example.resume.resumeproject.Entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AccountLockPolicyAdmins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String adminName;
    private String email;
    private String password;
    private String organization;

    private Boolean Authorized = false;



}