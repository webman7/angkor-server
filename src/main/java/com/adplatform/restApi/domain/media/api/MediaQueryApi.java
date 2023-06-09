package com.adplatform.restApi.domain.media.api;

import com.adplatform.restApi.domain.bank.domain.Bank;
import com.adplatform.restApi.domain.bank.service.BankFindUtils;
import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.media.dao.MediaFileRepository;
import com.adplatform.restApi.domain.media.dao.category.MediaCategoryRepository;
import com.adplatform.restApi.domain.media.domain.*;
import com.adplatform.restApi.domain.media.dto.MediaFileDto;
import com.adplatform.restApi.domain.media.dto.MediaMapper;
import com.adplatform.restApi.domain.media.exception.MediaNotFoundException;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.domain.media.dao.MediaRepository;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.service.MediaFindUtils;
import com.adplatform.restApi.domain.statistics.dao.taxbill.MediaTaxBillRepository;
import com.adplatform.restApi.domain.statistics.domain.taxbill.MediaTaxBill;
import com.adplatform.restApi.domain.statistics.dto.MediaTaxBillFileDto;
import com.adplatform.restApi.domain.statistics.dto.MediaTaxBillPaymentFileDto;
import com.adplatform.restApi.domain.statistics.dto.TaxBillDto;
import com.adplatform.restApi.domain.statistics.dto.TaxBillMapper;
import com.adplatform.restApi.domain.statistics.service.MediaTaxBillFindUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaQueryApi {

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    private final MediaFileRepository mediaFileRepository;
    private final MediaCategoryRepository mediaCategoryRepository;
    private final CompanyRepository companyRepository;
    private final MediaTaxBillRepository mediaTaxBillRepository;
    private final TaxBillMapper taxBillMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<MediaDto.Response.Search> search(
            @PageableDefault Pageable pageable,
            MediaDto.Request.Search searchRequest) {
        return PageDto.create(this.mediaRepository.search(pageable, searchRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MediaDto.Response.MediaInfo findById(@PathVariable Integer id) {
        Media media = this.mediaRepository.findById(id).orElseThrow(MediaNotFoundException::new);
        Company company = CompanyFindUtils.findByIdOrElseThrow(media.getCompany().getId(), this.companyRepository);
        List<Category> category = this.mediaCategoryRepository.findByMediaIdCategory(id);
        MediaFileDto.Response.FileInfo mediaFile = this.mediaRepository.findByMediaIdFileInfo(id);

        return this.mediaMapper.toCategoryResponse(MediaFindUtils.findByIdOrElseThrow(id, this.mediaRepository), company, category, mediaFile);
    }

    @GetMapping
    public ResponseEntity<List<MediaDto.Response.Default>> findAll() {
        List<MediaDto.Response.Default> media = this.mediaRepository.mediaAll();
        return ResponseEntity.ok(this.mediaMapper.toMediaResponse(media));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/tax/search")
    public PageDto<TaxBillDto.Response.TaxBill> searchTax(
            @PageableDefault Pageable pageable,
            @RequestBody @Valid TaxBillDto.Request.SearchTax searchRequest) {
        return PageDto.create(this.mediaTaxBillRepository.searchTax(pageable, searchRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/settlement/search")
    public PageDto<TaxBillDto.Response.TaxBill> searchSettlement(
            @PageableDefault Pageable pageable,
            @RequestBody @Valid TaxBillDto.Request.SearchTax searchRequest) {
        return PageDto.create(this.mediaTaxBillRepository.searchSettlement(pageable, searchRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tax/{id}")
    public TaxBillDto.Response.TaxBillInfo findTaxById(@PathVariable Integer id) {
        MediaTaxBill mediaTaxBill = this.mediaTaxBillRepository.findById(id).orElseThrow(MediaNotFoundException::new);

        Media media = this.mediaRepository.findById(mediaTaxBill.getMediaId()).orElseThrow(MediaNotFoundException::new);
        Company company = CompanyFindUtils.findByIdOrElseThrow(media.getCompany().getId(), this.companyRepository);

        MediaTaxBillFileDto.Response.FileInfo mediaTaxBillFile = this.mediaTaxBillRepository.findByMediaIdFileInfo(id);
        MediaTaxBillPaymentFileDto.Response.FileInfo mediaTaxBillPaymentFile = this.mediaTaxBillRepository.findByMediaIdPaymentFileInfo(id);

        return this.taxBillMapper.toResponse(MediaTaxBillFindUtils.findByIdOrElseThrow(id, this.mediaTaxBillRepository), media, company, mediaTaxBillFile, mediaTaxBillPaymentFile);
    }
}
