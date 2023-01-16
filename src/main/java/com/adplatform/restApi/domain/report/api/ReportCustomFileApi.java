package com.adplatform.restApi.domain.report.api;

import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.report.dao.custom.mapper.ReportCustomQueryMapper;
import com.adplatform.restApi.domain.report.dao.dashboard.mapper.ReportDashboardQueryMapper;
import com.adplatform.restApi.domain.report.dto.custom.ReportCustomDto;
import com.adplatform.restApi.domain.report.dto.dashboard.ReportDashboardDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report/download")
public class ReportCustomFileApi {

    private final ReportDashboardQueryMapper reportDashboardQueryMapper;
    private final ReportCustomQueryMapper reportCustomQueryMapper;

    @SneakyThrows
    @GetMapping("/custom/csv")
    public ResponseEntity<byte[]> downloadInfoCsv(@RequestBody @Valid ReportCustomDto.Request.Report request, @PageableDefault Pageable pageable) {

        List<ReportDashboardDto.Response.IndicatorColumn> indicatorContent = this.reportDashboardQueryMapper.indicatorColumn();
        List<String> indicators = request.getIndicators();

        List<String> HEADER_ITEMS = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        String fileName = request.getReportLevel()+"_"+request.getStartDate()+"_"+ request.getEndDate();

        if (request.getReportLevel().equals("AD_ACCOUNT")) {
            //////////////////////////////////////////////////
            // Header
            //////////////////////////////////////////////////
            HEADER_ITEMS.add("StartDate");
            HEADER_ITEMS.add("EndDate");
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
            List<ReportCustomDto.Response.Page> content = this.reportCustomQueryMapper.adAccountsDaily(request, pageable);

            sb.append(String.join(",", HEADER_ITEMS)).append("\n");
            content.forEach(c -> {
                sb.append(c.getStartDate()).append(",");
                sb.append(c.getEndDate()).append(",");
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
        } else if(request.getReportLevel().equals("CAMPAIGN"))
        {
            //////////////////////////////////////////////////
            // Header
            //////////////////////////////////////////////////
            HEADER_ITEMS.add("Campaign Type");
            HEADER_ITEMS.add("Campaign");
            HEADER_ITEMS.add("StartDate");
            HEADER_ITEMS.add("EndDate");
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
            List<ReportCustomDto.Response.Page> content = this.reportCustomQueryMapper.adAccountCampaignsDaily(request, pageable);

            sb.append(String.join(",", HEADER_ITEMS)).append("\n");
            content.forEach(c -> {
                sb.append(c.getAdTypeAndGoal().getAdTypeName()).append(",");
                sb.append(c.getCampaignName()).append(",");
                sb.append(c.getStartDate()).append(",");
                sb.append(c.getEndDate()).append(",");
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
        } else if(request.getReportLevel().equals("AD_GROUP")) {
            //////////////////////////////////////////////////
            // Header
            //////////////////////////////////////////////////
            HEADER_ITEMS.add("Campaign Type");
            HEADER_ITEMS.add("Campaign");
            HEADER_ITEMS.add("AdGroup");
            HEADER_ITEMS.add("StartDate");
            HEADER_ITEMS.add("EndDate");
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
            List<ReportCustomDto.Response.Page> content = this.reportCustomQueryMapper.adAccountAdGroupsDaily(request, pageable);

            sb.append(String.join(",", HEADER_ITEMS)).append("\n");
            content.forEach(c -> {
                sb.append(c.getAdTypeAndGoal().getAdTypeName()).append(",");
                sb.append(c.getCampaignName()).append(",");
                sb.append(c.getAdGroupName()).append(",");
                sb.append(c.getStartDate()).append(",");
                sb.append(c.getEndDate()).append(",");
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
        } else if(request.getReportLevel().equals("CREATIVE")) {
            //////////////////////////////////////////////////
            // Header
            //////////////////////////////////////////////////
            HEADER_ITEMS.add("Campaign Type");
            HEADER_ITEMS.add("Campaign");
            HEADER_ITEMS.add("AdGroup");
            HEADER_ITEMS.add("Creative");
            HEADER_ITEMS.add("StartDate");
            HEADER_ITEMS.add("EndDate");
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
            List<ReportCustomDto.Response.Page> content = this.reportCustomQueryMapper.adAccountCreativesDaily(request, pageable);

            sb.append(String.join(",", HEADER_ITEMS)).append("\n");
            content.forEach(c -> {
                sb.append(c.getAdTypeAndGoal().getAdTypeName()).append(",");
                sb.append(c.getCampaignName()).append(",");
                sb.append(c.getAdGroupName()).append(",");
                sb.append(c.getCreativeName()).append(",");
                sb.append(c.getStartDate()).append(",");
                sb.append(c.getEndDate()).append(",");


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
