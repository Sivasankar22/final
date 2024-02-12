package com.example.resume.resumeproject.Repository;

import com.example.resume.resumeproject.Entities.Jobs;
import com.example.resume.resumeproject.RequestDtos.AddJobRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JobsRepository extends JpaRepository<Jobs,Integer> {
    List<Jobs> findAllByOrderByCreatedOnDesc();
}
