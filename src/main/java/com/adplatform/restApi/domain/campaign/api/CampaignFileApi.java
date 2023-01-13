package com.adplatform.restApi.domain.campaign.api;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.campaign.dao.campaign.mapper.CampaignQueryMapper;
import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/campaigns/download")
public class CampaignFileApi {

    private final CampaignQueryMapper campaignQueryMapper;

//    private static final List<String> HEADER_CASH_ITEMS = List.of(
//            "광고계정 ID",
//            "광고계정",
//            "상태",
//            "유상캐시 잔액",
//            "무상캐시 잔액");

    @SneakyThrows
    @GetMapping("/dashboard/csv")
    public ResponseEntity<byte[]> downloadInfoCsv(@RequestBody @Valid AdvertiserSearchRequest request) {
        List<CampaignDto.Response.Page> content = this.campaignQueryMapper.searchCampaignSelectQuery(request);

//        StringBuilder sb = new StringBuilder();
//        sb.append(String.join(",", HEADER_MY_ITEMS)).append("\n");
//        content.forEach(c -> sb.append(
//                String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
//                        c.getId(),
//                        c.getName(),
//                        this.getStatus(c.getConfig(), c.isAdminStop(), c.isOutOfBalance()),
//                        this.getCompanyType(c.getCompanyType()),
//                        c.isPreDeferredPayment() ? "후불" : "선불",
//                        c.getCreditLimit(),
//                        c.getWalletSpend().getCash(),
//                        c.getWalletSpend().getTodaySpend(),
//                        c.getWalletSpend().getYesterdaySpend(),
//                        c.getWalletSpend().getMonthSpend())
//        ));
//
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0xEF);
        baos.write(0xBB);
        baos.write(0xBF);
//        baos.write(sb.toString().getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dashboard-campaign-list.csv")
                .contentType(new MediaType("text", "csv"))
                .body(baos.toByteArray());
    }
}
