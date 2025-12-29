package com.example.crm.repository;

import com.example.crm.entity.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {
    List<ApplicationRequest> findAllByHandledIsFalse();
}

