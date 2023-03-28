package com.adplatform.restApi.domain.media.dao;

import com.adplatform.restApi.domain.media.domain.FileInformation;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.MediaFileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MediaQuerydslRepository {

    Page<MediaDto.Response.Search> search(Pageable pageable, MediaDto.Request.Search searchRequest);

    String findByMediaIdFileUrl(Integer id);

    FileInformation.FileType findByMediaIdFileType(Integer id);

    Integer findByMediaIdFileId(Integer id);

    MediaFileDto.Response.FileInfo findByMediaIdFileInfo(Integer id);

    List<MediaDto.Response.Default> mediaAll();




    // 이전 작업
    void deleteByAdGroupId(Integer adGroupId);
}
