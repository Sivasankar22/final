package com.example.resume.resumeproject.Repository;

import com.example.resume.resumeproject.Entities.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApplicantRepository extends JpaRepository<Applicant,Integer> {
    Applicant findApplicantByEmail(String Email);

    List<Applicant> findAllByOrderByFirstNameAsc();
    List<Applicant> findAllByOrderByExperienceDesc();

    List<Applicant> findAllByCurrentJobTitle(String jobTitle);
    List<Applicant> findAllByFirstName(String Name);

    List<Applicant> findAllByExperience(Integer Experience);
}
