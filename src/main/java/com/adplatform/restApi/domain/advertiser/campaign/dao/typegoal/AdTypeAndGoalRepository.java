package com.adplatform.restApi.domain.advertiser.campaign.dao.typegoal;

import com.adplatform.restApi.domain.advertiser.campaign.domain.AdTypeAndGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author junny
 * @since 1.0
 */
public interface AdTypeAndGoalRepository extends JpaRepository<AdTypeAndGoal, Integer>, AdTypeAndGoalQuerydslRepository {
    Optional<AdTypeAndGoal> findByAdType_NameAndAdGoal_Name(String adTypeName, String adGoalName);
}
