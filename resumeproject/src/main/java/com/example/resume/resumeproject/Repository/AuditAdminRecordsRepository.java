package com.example.resume.resumeproject.Repository;

import com.example.resume.resumeproject.Entities.AuditAdminRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuditAdminRecordsRepository extends JpaRepository<AuditAdminRecords,Integer> {
}
