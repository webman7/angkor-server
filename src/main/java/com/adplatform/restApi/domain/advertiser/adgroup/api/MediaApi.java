package com.adplatform.restApi.domain.advertiser.adgroup.api;

import com.adplatform.restApi.domain.media.dto.MediaMapper;
import com.adplatform.restApi.domain.media.dao.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaApi {
    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;

    /*
    @GetMapping
    public ResponseEntity<List<MediaDto.Response.Default>> findAll() {
        return ResponseEntity.ok(this.mediaMapper.toDefaultResponse(this.mediaRepository.findAll()));
    }

     */
}
