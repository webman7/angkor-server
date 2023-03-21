package com.adplatform.restApi.domain.media.service;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.domain.media.dao.category.CategoryRepository;
import com.adplatform.restApi.domain.media.dao.category.MediaCategoryRepository;
import com.adplatform.restApi.domain.media.domain.*;
import com.adplatform.restApi.domain.media.dao.MediaRepository;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.MediaMapper;
import com.adplatform.restApi.domain.media.dto.category.MediaCategoryDto;
import com.adplatform.restApi.domain.media.exception.MediaUpdateException;
import com.adplatform.restApi.domain.media.exception.CategoryNotFoundException;
import com.adplatform.restApi.infra.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class MediaSaveService {

    private final MediaMapper mediaMapper;
    private final MediaRepository mediaRepository;
    private final MediaCategoryRepository mediaCategoryRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    private final FileService fileService;
    public void save(MediaDto.Request.Save request) {
        Company company = CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository);
        Media media = this.mediaMapper.toEntity(request, company);
        request.getMediaFiles().forEach(file -> media.addMediaFile(this.saveMediaFile(request, media, file)));
        this.mediaRepository.save(media);
    }

    public void update(MediaDto.Request.Update request) {
        try{
            Media media = MediaFindUtils.findByIdOrElseThrow(request.getId(), this.mediaRepository).update(request);
            if(request.getMediaFiles().size() > 0) {
                request.getMediaFiles().forEach(file -> media.addMediaFile(this.saveMediaFile(request, media, file)));
            }
        }catch (Exception e){
            throw new MediaUpdateException();
        }
    }

    public void saveAdmin(MediaDto.Request.Save request) {
        Company company = CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository);
        Media media = this.mediaMapper.toEntity(request, company);
        if(request.getMediaFiles().size() > 0) {
            request.getMediaFiles().forEach(file -> media.addMediaFile(this.saveMediaFile(request, media, file)));
        }
        Integer mediaId = this.mediaRepository.save(media).getId();

        // 루프 돌면서 인서트
        List<Category> category = this.findByCategoryId(request.getCategory());

        for (Category ca: category) {
            System.out.println("=============================");
            System.out.println(ca.getId());
            this.mediaCategoryRepository.insertMediaCategory(mediaId, ca.getId());
        }
    }

    public void updateAdmin(MediaDto.Request.Update request) {
        try{
            Media media = MediaFindUtils.findByIdOrElseThrow(request.getId(), this.mediaRepository).update(request);
            if(request.getMediaFiles().size() > 0) {
                request.getMediaFiles().forEach(file -> media.addMediaFile(this.saveMediaFile(request, media, file)));
            }
        }catch (Exception e){
            throw new MediaUpdateException();
        }
    }

    private List<Category> findByCategoryId(List<Integer> categoryId) {
        return categoryId.stream().map(this::findByCategoryIdOrElseThrow).collect(Collectors.toList());
    }

    private Category findByCategoryIdOrElseThrow(Integer categoryId) {
        return this.categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public void delete(Integer id) {
        MediaFindUtils.findByIdOrElseThrow(id, this.mediaRepository).delete();
    }

    @SneakyThrows
    private MediaFile saveMediaFile(MediaDto.Request.Save request, Media media, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
        String savedFileUrl = this.fileService.saveMedia(request, file);
        int index = savedFileUrl.lastIndexOf("/");
        String savedFilename = savedFileUrl.substring(index+1);
        return new MediaFile(media, this.mediaFileInformation(file, savedFileUrl, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private FileInformation mediaFileInformation(MultipartFile file, String savedFileUrl, String savedFilename, String originalFilename, String mimetype) {
        FileInformation.FileType fileType;
        if (mimetype.startsWith("image")) {
            fileType = FileInformation.FileType.IMAGE;
        } else if (mimetype.startsWith("application/pdf")) {
            fileType = FileInformation.FileType.PDF;
        } else {
            throw new UnsupportedOperationException();
        }

        return FileInformation.builder()
                .url(savedFileUrl)
                .fileType(fileType)
                .fileSize(file.getSize())
                .filename(savedFilename)
                .originalFileName(originalFilename)
                .mimeType(mimetype)
                .build();
    }
}
