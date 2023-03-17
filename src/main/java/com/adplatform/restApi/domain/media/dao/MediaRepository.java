package com.adplatform.restApi.domain.media.dao;

import com.adplatform.restApi.domain.media.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Integer>, MediaQuerydslRepository {
}
