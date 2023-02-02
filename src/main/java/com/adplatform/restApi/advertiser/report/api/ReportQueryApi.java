package com.adplatform.restApi.advertiser.report.api;

import com.adplatform.restApi.advertiser.report.dao.custom.ReportCustomRepository;
import com.adplatform.restApi.advertiser.report.dao.custom.mapper.ReportCustomQueryMapper;
import com.adplatform.restApi.advertiser.report.dto.custom.ReportCustomDto;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportQueryApi {

    private final ReportCustomRepository reportCustomRepository;
    private final ReportCustomQueryMapper reportCustomQueryMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/custom/search")
    public PageDto<ReportCustomDto.Response.Default> search(
            @PageableDefault Pageable pageable,
            ReportCustomDto.Request.Search request) {
        return PageDto.create(this.reportCustomRepository.search(pageable, request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/custom/{id}")
    public ReportCustomDto.Response.Default reportCustomDetailInfo (@PathVariable(name = "id") Integer id) {
        return this.reportCustomRepository.reportCustomDetailInfo(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/custom/adAccounts")
    public PageDto<ReportCustomDto.Response.Page> adAccountsDailyTotal(
            @RequestBody @Valid ReportCustomDto.Request.Report request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.reportCustomQueryMapper.adAccountsDailyTotal(request, pageable),
                pageable,
                this.reportCustomQueryMapper.countAdAccountsDailyTotal(request)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/custom/adAccounts/daily")
    public PageDto<ReportCustomDto.Response.Page> adAccountsDaily(
            @RequestBody @Valid ReportCustomDto.Request.Report request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.reportCustomQueryMapper.adAccountsDaily(request, pageable),
                pageable,
                this.reportCustomQueryMapper.countAdAccountsDaily(request)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/custom/campaigns")
    public PageDto<ReportCustomDto.Response.Page> campaignsDailyTotal(
            @RequestBody @Valid ReportCustomDto.Request.Report request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.reportCustomQueryMapper.campaignsDailyTotal(request, pageable),
                pageable,
                this.reportCustomQueryMapper.countCampaignsDailyTotal(request)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/custom/campaigns/daily")
    public PageDto<ReportCustomDto.Response.Page> campaignsDaily(
            @RequestBody @Valid ReportCustomDto.Request.Report request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.reportCustomQueryMapper.campaignsDaily(request, pageable),
                pageable,
                this.reportCustomQueryMapper.countCampaignsDaily(request)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/custom/adGroups")
    public PageDto<ReportCustomDto.Response.Page> adGroupsDailyTotal(
            @RequestBody @Valid ReportCustomDto.Request.Report request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.reportCustomQueryMapper.adGroupsDailyTotal(request, pageable),
                pageable,
                this.reportCustomQueryMapper.countAdGroupsDailyTotal(request)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/custom/adGroups/daily")
    public PageDto<ReportCustomDto.Response.Page> adGroupsDaily(
            @RequestBody @Valid ReportCustomDto.Request.Report request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.reportCustomQueryMapper.adGroupsDaily(request, pageable),
                pageable,
                this.reportCustomQueryMapper.countAdGroupsDaily(request)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/custom/creatives")
    public PageDto<ReportCustomDto.Response.Page> creativesDailyTotal(
            @RequestBody @Valid ReportCustomDto.Request.Report request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.reportCustomQueryMapper.creativesDailyTotal(request, pageable),
                pageable,
                this.reportCustomQueryMapper.countCreativesDailyTotal(request)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/custom/creatives/daily")
    public PageDto<ReportCustomDto.Response.Page> creativesDaily(
            @RequestBody @Valid ReportCustomDto.Request.Report request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.reportCustomQueryMapper.creativesDaily(request, pageable),
                pageable,
                this.reportCustomQueryMapper.countCreativesDaily(request)));
    }

}
