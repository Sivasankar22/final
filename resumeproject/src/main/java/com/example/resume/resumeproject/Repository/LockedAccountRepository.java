package com.example.resume.resumeproject.Repository;

import com.example.resume.resumeproject.Entities.AccountLockPolicyAdmins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockedAccountRepository extends JpaRepository<AccountLockPolicyAdmins,Integer> {

    AccountLockPolicyAdmins findAdminByEmail(String Email);
}
