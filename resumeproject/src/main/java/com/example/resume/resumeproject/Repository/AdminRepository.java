package com.example.resume.resumeproject.Repository;

import com.example.resume.resumeproject.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Admin findAdminByEmail(String Email);
    Admin findAdminByAdminName(String Name);
}
