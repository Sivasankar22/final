package com.example.resume.resumeproject.Repository;

import com.example.resume.resumeproject.Entities.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin,Integer> {

    SuperAdmin findBySuperAdminName(String Name);
}
