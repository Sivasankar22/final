package com.example.resume.resumeproject.Repository;

import com.example.resume.resumeproject.Entities.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JobsRepository extends JpaRepository<Jobs,Integer> {
}
