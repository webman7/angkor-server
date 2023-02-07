package com.adplatform.restApi.domain.advertiser.adgroup.dao.media;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface MediaRepository extends JpaRepository<Media, Integer>, MediaQuerydslRepository {
    Optional<Media> findByName(String name);
}
