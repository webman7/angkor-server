package com.adplatform.restApi.domain.adgroup.dao;

import com.adplatform.restApi.domain.adgroup.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Integer> {
    Optional<Media> findByName(String name);
}
