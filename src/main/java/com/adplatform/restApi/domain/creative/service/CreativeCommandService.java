
package com.adplatform.restApi.domain.creative.service;

import com.adplatform.restApi.domain.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.creative.domain.Creative;
import com.adplatform.restApi.domain.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.creative.domain.CreativeOpinionProofFile;
import com.adplatform.restApi.domain.creative.domain.FileInformation;
import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.creative.dto.CreativeMapper;
import com.adplatform.restApi.infra.file.util.ImageSizeUtils;
import com.adplatform.restApi.infra.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class CreativeCommandService {
    private final CreativeRepository creativeRepository;
    private final FileService fileService;
    private final CreativeMapper creativeMapper;

    public void save(CreativeDto.Request.Save request) {
        Creative creative = this.creativeMapper.toEntity(request);
        request.getFiles().forEach(file -> creative.addFile(this.saveFile(request, creative, file)));
        request.getOpinionProofFiles().forEach(file -> creative.addOpinionProofFile(this.saveOpinionProofFile(creative, file)));
        this.creativeRepository.save(creative);
    }

    public void update(CreativeDto.Request.Update request) {
        Creative creative = CreativeFindUtils.findByIdOrElseThrow(request.getCreativeId(), this.creativeRepository)
                .update(request)
                .deleteOpinionProofFiles(request.getDeleteOpinionProofFilenames(), this.fileService);
        request.getOpinionProofFiles().forEach(file -> creative.addOpinionProofFile(this.saveOpinionProofFile(creative, file)));
    }

    @SneakyThrows
    private CreativeFile saveFile(CreativeDto.Request.Save request, Creative creative, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
        String savedFilename = this.fileService.save(file);
        return new CreativeFile(creative, request.getType(), this.createFileInformation(file, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private CreativeOpinionProofFile saveOpinionProofFile(Creative creative, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
        String savedFilename = this.fileService.save(file);
        return new CreativeOpinionProofFile(creative, this.createFileInformation(file, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private FileInformation createFileInformation(MultipartFile file, String savedFilename, String originalFilename, String mimetype) {
        FileInformation.FileType fileType;
        int width = 0;
        int height = 0;
        if (mimetype.startsWith("image")) {
            fileType = FileInformation.FileType.IMAGE;
            width = ImageSizeUtils.getWidth(file);
            height = ImageSizeUtils.getHeight(file);
        } else if (mimetype.startsWith("video")) {
            fileType = FileInformation.FileType.VIDEO;
        } else {
            throw new UnsupportedOperationException();
        }
        return FileInformation.builder()
                .url("")
                .fileType(fileType)
                .fileSize(file.getSize())
                .filename(savedFilename)
                .originalFileName(originalFilename)
                .width(width)
                .height(height)
                .mimeType(mimetype)
                .build();
    }

    public void delete(Integer id) {
        CreativeFindUtils.findByIdOrElseThrow(id, this.creativeRepository).delete();
    }

    public void changeConfig(Integer id, Creative.Config config) {
        Creative creative = CreativeFindUtils.findByIdOrElseThrow(id, this.creativeRepository);
        if (config == Creative.Config.ON) creative.changeConfigOn();
        else if (config == Creative.Config.OFF) creative.changeConfigOff();
    }
}