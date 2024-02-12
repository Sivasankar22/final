package com.example.resume.resumeproject.Repository;

import com.example.resume.resumeproject.Entities.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty,Integer> {

    ThirdParty findByOrganization(String Name);


}
