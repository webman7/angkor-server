package com.adplatform.restApi.domain.media.dao;

import com.adplatform.restApi.domain.media.domain.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaFileRepository extends JpaRepository<MediaFile, Integer> {
}
