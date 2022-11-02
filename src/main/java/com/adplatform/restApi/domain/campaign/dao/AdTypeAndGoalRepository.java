package com.adplatform.restApi.domain.campaign.dao;

import com.adplatform.restApi.domain.campaign.domain.AdTypeAndGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdTypeAndGoalRepository extends JpaRepository<AdTypeAndGoal, Integer> {
    Optional<AdTypeAndGoal> findByAdType_NameAndAdGoal_Name(String adTypeName, String adGoalName);
}
