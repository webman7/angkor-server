package com.adplatform.restApi.infra.file.service;

import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementDto;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//@Profile({"prod"})
@Service
public class AwsS3FileServiceImpl implements FileService {
    @Override
    public String save(CreativeDto.Request.Save request, MultipartFile file) {
        return null;
    }

    @Override
    public String saveProofFile(Creative creative, MultipartFile file) {
        return null;
    }

    @Override
    public String saveCompany(CompanyDto.Request.Save request, MultipartFile file, String fType) {
        return null;
    }
    @Override
    public String saveWalletCharge(WalletDto.Request.SaveCredit request, MultipartFile file) {
        return null;
    }
    @Override
    public String saveWalletRefund(WalletDto.Request.UpdateRefund request, MultipartFile file) {
        return null;
    }
    @Override
    public String saveMedia(MediaDto.Request.Save request, MultipartFile file) {
        return null;
    }
    @Override
    public String saveMediaPlacement(MediaPlacementDto.Request.Save request, MultipartFile file) {
        return null;
    }

    @Override
    public byte[] findByName(String filename) {
        return new byte[0];
    }

    @Override
    public void delete(Integer adGroupId, String filename) {

    }

    @Override
    public void deleteOpinionProofFiles(Integer adGroupId, String filename) {

    }
}