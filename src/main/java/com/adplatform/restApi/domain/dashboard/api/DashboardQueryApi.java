package com.adplatform.restApi.domain.dashboard.api;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.dashboard.dao.dashboard.mapper.DashboardQueryMapper;
import com.adplatform.restApi.domain.dashboard.dto.DashboardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dashboard")
public class DashboardQueryApi {
    private final AdAccountRepository adAccountRepository;
    private final DashboardQueryMapper dashboardQueryMapper;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/adaccounts/countByAd")
    public DashboardDto.Response.AdAccountCountByAd adAccountCountByAd(
            @RequestBody @Valid DashboardDto.Request.AdAccountDashboard request) {
        return this.adAccountRepository.adAccountsCountByAd(request.getAdAccountId());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/adaccounts/cost")
    public DashboardDto.Response.AdAccountDashboardCost adAccountDashboardCost(
            @RequestBody @Valid DashboardDto.Request.AdAccountDashboard request) {
        return this.dashboardQueryMapper.adAccountsDashboardCost(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/adaccounts/chart")
    public List<DashboardDto.Response.DashboardChart> adAccountDashboardChart(
            @RequestBody @Valid DashboardDto.Request.TotalDashboardChart request) {
        return this.dashboardQueryMapper.adAccountsDashboardChart(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/campaigns/chart")
    public List<DashboardDto.Response.DashboardChart> campaignsDashboardChart(
            @RequestBody @Valid DashboardDto.Request.TotalDashboardChart request) {
        return this.dashboardQueryMapper.campaignsDashboardChart(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/campaigns/{id}/chart")
    public List<DashboardDto.Response.DashboardChart> campaignByIdDashboardChart(
            @PathVariable(name = "id") Integer campaignId,
            @RequestBody @Valid DashboardDto.Request.DashboardChart request) {
        return this.dashboardQueryMapper.campaignByIdDashboardChart(request, campaignId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/adgroups/chart")
    public List<DashboardDto.Response.DashboardChart> adGroupsDashboardChart(
            @RequestBody @Valid DashboardDto.Request.TotalDashboardChart request) {
        return this.dashboardQueryMapper.adGroupsDashboardChart(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/adgroups/{id}/chart")
    public List<DashboardDto.Response.DashboardChart> adGroupByIdDashboardChart(
            @PathVariable(name = "id") Integer adGroupId,
            @RequestBody @Valid DashboardDto.Request.DashboardChart request) {
        return this.dashboardQueryMapper.adGroupByIdDashboardChart(request, adGroupId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/creatives/chart")
    public List<DashboardDto.Response.DashboardChart> creativesDashboardChart(
            @RequestBody @Valid DashboardDto.Request.TotalDashboardChart request) {
        return this.dashboardQueryMapper.creativesDashboardChart(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/creatives/{id}/chart")
    public List<DashboardDto.Response.DashboardChart> creativeByIdDashboardChart(
            @PathVariable(name = "id") Integer creativeId,
            @RequestBody @Valid DashboardDto.Request.DashboardChart request) {
        return this.dashboardQueryMapper.creativeByIdDashboardChart(request);
    }

}
