package com.adplatform.restApi.domain.report.api;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.mapper.AdGroupQueryMapper;
import com.adplatform.restApi.domain.adgroup.domain.Media;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.campaign.dao.campaign.mapper.CampaignQueryMapper;
import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.creative.dao.mapper.CreativeQueryMapper;
import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.report.dao.dashboard.mapper.ReportDashboardQueryMapper;
import com.adplatform.restApi.domain.report.dto.dashboard.ReportDashboardDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report/download")
public class ReportDashboardFileApi {

    private final CampaignQueryMapper campaignQueryMapper;
    private final AdGroupQueryMapper adGroupQueryMapper;
    private final CreativeQueryMapper creativeQueryMapper;
    private final ReportDashboardQueryMapper reportDashboardQueryMapper;

    @SneakyThrows
    @PostMapping("/dashboard/csv")
    public ResponseEntity<byte[]> downloadInfoCsv(@RequestBody @Valid AdvertiserSearchRequest request, @PageableDefault Pageable pageable) {

        List<ReportDashboardDto.Response.IndicatorColumn> indicatorContent = this.reportDashboardQueryMapper.indicatorColumn();
        List<String> indicators = request.getIndicators();

        List<String> HEADER_ITEMS = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        String fileName = request.getReportLevel()+"_"+request.getReportStartDate()+"_"+ request.getReportEndDate();

        // Campaign
        if(request.getReportLevel().equals("CAMPAIGN"))
        {
            //////////////////////////////////////////////////
            // Header
            //////////////////////////////////////////////////
            HEADER_ITEMS.add("Campaign");
            HEADER_ITEMS.add("Campaign ID");
            HEADER_ITEMS.add("ON/OFF");
            HEADER_ITEMS.add("Campaign Type");
            HEADER_ITEMS.add("Budget");
            // Indicator Add
            for(String indicator : indicators) {
                for (ReportDashboardDto.Response.IndicatorColumn str : indicatorContent) {
                    if (str.getColumnIndex().equals(indicator)) {
                        HEADER_ITEMS.add(str.getColumnName());
                    }
                }
            }
            HEADER_ITEMS.add("Period");

            //////////////////////////////////////////////////
            // Contents
            //////////////////////////////////////////////////
            List<CampaignDto.Response.Page> content = this.campaignQueryMapper.searchCampaignList(request, pageable);

            sb.append(String.join(",", HEADER_ITEMS)).append("\n");
            content.forEach(c -> {
                sb.append(c.getName()).append(",");
                sb.append(c.getId()).append(",");
                sb.append(c.getConfig()).append(",");
                sb.append(c.getAdTypeAndGoal().getAdTypeName()).append(",");
                sb.append(c.getBudgetAmount()).append(",");
                for(String indicator : indicators) {
                    switch (indicator) {
                        case "cost": sb.append(c.getReport().getCost()).append(",");
                            break;
                        case "impression": sb.append(c.getReport().getImpression()).append(",");
                            break;
                        case "click": sb.append(c.getReport().getClick()).append(",");
                            break;
                        case "ctr": sb.append(c.getReport().getCtr()).append(",");
                            break;
                        case "cost_per_impression": sb.append(c.getReport().getCpm()).append(",");
                            break;
                        case "cost_per_click": sb.append(c.getReport().getCpc()).append(",");
                            break;
                    }
                }
                sb.append(c.getStartDate()).append("~").append(c.getEndDate()).append(",");

                if(sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append("\n");
            });
        } else if(request.getReportLevel().equals("AD_GROUP")) {
            //////////////////////////////////////////////////
            // Header
            //////////////////////////////////////////////////
            HEADER_ITEMS.add("AdGroup");
            HEADER_ITEMS.add("AdGroup ID");
            HEADER_ITEMS.add("ON/OFF");
            HEADER_ITEMS.add("Budget");
            HEADER_ITEMS.add("Pacing Type");
            HEADER_ITEMS.add("Bidding Amount");
            // Indicator Add
            for(String indicator : indicators) {
                for (ReportDashboardDto.Response.IndicatorColumn str : indicatorContent) {
                    if (str.getColumnIndex().equals(indicator)) {
                        HEADER_ITEMS.add(str.getColumnName());
                    }
                }
            }
            HEADER_ITEMS.add("Period");

            //////////////////////////////////////////////////
            // Contents
            //////////////////////////////////////////////////
            List<AdGroupDto.Response.AdvertiserSearch> content = this.adGroupQueryMapper.searchAdGroupList(request, pageable);

            sb.append(String.join(",", HEADER_ITEMS)).append("\n");
            content.forEach(c -> {
                sb.append(c.getName()).append(",");
                sb.append(c.getId()).append(",");
                sb.append(c.getConfig()).append(",");
                sb.append(c.getBudgetAmount()).append(",");
                sb.append(c.getPacingType()).append(",");
                sb.append(c.getBidAmount()).append(",");
                for(String indicator : indicators) {
                    switch (indicator) {
                        case "cost": sb.append(c.getReport().getCost()).append(",");
                            break;
                        case "impression": sb.append(c.getReport().getImpression()).append(",");
                            break;
                        case "click": sb.append(c.getReport().getClick()).append(",");
                            break;
                        case "ctr": sb.append(c.getReport().getCtr()).append(",");
                            break;
                        case "cost_per_impression": sb.append(c.getReport().getCpm()).append(",");
                            break;
                        case "cost_per_click": sb.append(c.getReport().getCpc()).append(",");
                            break;
                    }
                }
                sb.append(c.getScheduleStartDate()).append("~").append(c.getScheduleEndDate()).append(",");

                if(sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append("\n");
            });
        } else if(request.getReportLevel().equals("CREATIVE")) {
            //////////////////////////////////////////////////
            // Header
            //////////////////////////////////////////////////
            HEADER_ITEMS.add("Creative");
            HEADER_ITEMS.add("Creative ID");
            HEADER_ITEMS.add("ON/OFF");
            HEADER_ITEMS.add("AdGroup");
            HEADER_ITEMS.add("AdGroup ID");
            HEADER_ITEMS.add("Campaign");
            HEADER_ITEMS.add("Campaign ID");
            // Indicator Add
            for(String indicator : indicators) {
                for (ReportDashboardDto.Response.IndicatorColumn str : indicatorContent) {
                    if (str.getColumnIndex().equals(indicator)) {
                        HEADER_ITEMS.add(str.getColumnName());
                    }
                }
            }

            //////////////////////////////////////////////////
            // Contents
            //////////////////////////////////////////////////
            List<CreativeDto.Response.Default> content = this.creativeQueryMapper.searchCreativeList(request, pageable);

            sb.append(String.join(",", HEADER_ITEMS)).append("\n");
            content.forEach(c -> {
                sb.append(c.getName()).append(",");
                sb.append(c.getId()).append(",");
                sb.append(c.getConfig()).append(",");
                sb.append(c.getAdGroupName()).append(",");
                sb.append(c.getAdGroupId()).append(",");
                sb.append(c.getCampaignName()).append(",");
                sb.append(c.getCampaignId()).append(",");
                for(String indicator : indicators) {
                    switch (indicator) {
                        case "cost": sb.append(c.getReport().getCost()).append(",");
                            break;
                        case "impression": sb.append(c.getReport().getImpression()).append(",");
                            break;
                        case "click": sb.append(c.getReport().getClick()).append(",");
                            break;
                        case "ctr": sb.append(c.getReport().getCtr()).append(",");
                            break;
                        case "cost_per_impression": sb.append(c.getReport().getCpm()).append(",");
                            break;
                        case "cost_per_click": sb.append(c.getReport().getCpc()).append(",");
                            break;
                    }
                }

                if(sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append("\n");
            });
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0xEF);
        baos.write(0xBB);
        baos.write(0xBF);
        baos.write(sb.toString().getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName+".csv")
                .contentType(new MediaType("text", "csv"))
                .body(baos.toByteArray());
    }


}
