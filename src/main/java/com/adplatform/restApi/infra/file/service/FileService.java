package com.adplatform.restApi.infra.file.service;

import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementDto;
import com.adplatform.restApi.domain.wallet.domain.WalletReserveLog;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author junny
 * @since 1.0
 */
public interface FileService {
    String save(CreativeDto.Request.Save request, MultipartFile file);

    String saveProofFile(Creative creative, MultipartFile file);

    String saveCompany(CompanyDto.Request.Save request, MultipartFile file, String fType);

    String saveWalletCharge(WalletDto.Request.SaveCredit request, MultipartFile file);

    String saveWalletRefund(WalletDto.Request.UpdateRefund request, MultipartFile file);

    String saveMedia(MediaDto.Request.Save request, MultipartFile file);

    String saveMediaPlacement(MediaPlacementDto.Request.Save request, MultipartFile file);

    byte[] findByName(String filename);

    void delete(Integer adGroupId, String filename);

    void deleteOpinionProofFiles(Integer adGroupId, String filename);
}
