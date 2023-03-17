package com.adplatform.restApi.domain.media.dao;

import com.adplatform.restApi.domain.media.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Integer>, MediaQuerydslRepository {
    Optional<Media> findByName(String name);
}
