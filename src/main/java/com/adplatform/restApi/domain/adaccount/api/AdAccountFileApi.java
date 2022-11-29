package com.adplatform.restApi.domain.adaccount.api;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/adaccounts/download")
public class AdAccountFileApi {
    private final AdAccountRepository adAccountRepository;

    private static final List<String> MY_HEADER_ITEMS = List.of(
            "광고계정 ID",
            "광고계정",
            "상태",
            "광고계정 유형",
            "지불방식",
            "후불한도",
            "잔액",
            "오늘 소진액",
            "어제 소진액",
            "이번달 소진액");

    private static final List<String> PAYBALNCE_HEADER_ITEMS = List.of(
            "광고계정 ID",
            "광고계정",
            "상태",
            "마케터명",
            "후불한도",
            "유상캐시 잔액");

    @SneakyThrows
    @GetMapping("/my/csv")
    public ResponseEntity<byte[]> downloadMyCsv() {
        List<AdAccountDto.Response.ForAgencySearch> content = this.adAccountRepository.searchForAgency(
                new AdAccountDto.Request.ForAgencySearch(), SecurityUtils.getLoginUserId());
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", MY_HEADER_ITEMS)).append("\n");
        content.forEach(c -> sb.append(
                String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                        c.getId(),
                        c.getName(),
                        this.getStatus(c.getConfig(), c.isAdminStop(), c.isOutOfBalance()),
                        this.getCompanyType(c.getCompanyType()),
                        c.isPreDeferredPayment() ? "후불" : "선불",
                        c.getCreditLimit(),
                        c.getWalletSpend().getCash(),
                        c.getWalletSpend().getTodaySpend(),
                        c.getWalletSpend().getYesterdaySpend(),
                        c.getWalletSpend().getMonthSpend())
        ));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0xEF);
        baos.write(0xBB);
        baos.write(0xBF);
        baos.write(sb.toString().getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=adaccount-my-list.csv")
                .contentType(new MediaType("text", "csv"))
                .body(baos.toByteArray());
    }

    @SneakyThrows
    @GetMapping("/my/excel")
    public ResponseEntity<byte[]> downloadMyExcel() {
        ByteArrayOutputStream baos;
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("adaccount");

            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < MY_HEADER_ITEMS.size(); i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(MY_HEADER_ITEMS.get(i));
            }

            List<AdAccountDto.Response.ForAgencySearch> content = this.adAccountRepository.searchForAgency(
                    new AdAccountDto.Request.ForAgencySearch(), SecurityUtils.getLoginUserId());

            int rowNum = 1;
            for (AdAccountDto.Response.ForAgencySearch data : content) {
                XSSFRow row = sheet.createRow(rowNum++);

                int columnNum = 0;
                XSSFCell cell = row.createCell(columnNum++);
                cell.setCellValue(data.getId());

                XSSFCell cell1 = row.createCell(columnNum++);
                cell1.setCellValue(data.getName());

                XSSFCell cell2 = row.createCell(columnNum++);
                cell2.setCellValue(this.getStatus(data.getConfig(), data.isAdminStop(), data.isOutOfBalance()));

                XSSFCell cell3 = row.createCell(columnNum++);
                cell3.setCellValue(this.getCompanyType(data.getCompanyType()));

                XSSFCell cell4 = row.createCell(columnNum++);
                cell4.setCellValue(data.isPreDeferredPayment() ? "후불" : "선불");

                XSSFCell cell5 = row.createCell(columnNum++);
                cell5.setCellValue(data.getCreditLimit());

                XSSFCell cell6 = row.createCell(columnNum++);
                cell6.setCellValue(data.getWalletSpend().getCash());

                XSSFCell cell7 = row.createCell(columnNum++);
                cell7.setCellValue(data.getWalletSpend().getTodaySpend());

                XSSFCell cell8 = row.createCell(columnNum++);
                cell8.setCellValue(data.getWalletSpend().getYesterdaySpend());

                XSSFCell cell9 = row.createCell(columnNum);
                cell9.setCellValue(data.getWalletSpend().getMonthSpend());
            }

            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("adaccount-my-list.xlsx").build());
        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
        headers.setContentType(new MediaType("application", "vnd.ms-excel"));
        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }

    @SneakyThrows
    @GetMapping("/paybalance/csv")
    public ResponseEntity<byte[]> downloadPayBalanceCsv() {
        List<AdAccountDto.Response.ForAgencySearch> content = this.adAccountRepository.searchForAgency(
                new AdAccountDto.Request.ForAgencySearch(), SecurityUtils.getLoginUserId());
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", PAYBALNCE_HEADER_ITEMS)).append("\n");
        content.forEach(c -> sb.append(
                String.format("%s,%s,%s,%s,%s,%s\n",
                        c.getId(),
                        c.getName(),
                        this.getStatus(c.getConfig(), c.isAdminStop(), c.isOutOfBalance()),
                        c.getMarketerName(),
                        c.getCreditLimit(),
                        c.getWalletSpend().getCash())
        ));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0xEF);
        baos.write(0xBB);
        baos.write(0xBF);
        baos.write(sb.toString().getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=adaccount-paybalance-list.csv")
                .contentType(new MediaType("text", "csv"))
                .body(baos.toByteArray());
    }

    @SneakyThrows
    @GetMapping("/paybalance/excel")
    public ResponseEntity<byte[]> downloadPayBalanceExcel() {
        ByteArrayOutputStream baos;
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("adaccount");

            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < PAYBALNCE_HEADER_ITEMS.size(); i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(PAYBALNCE_HEADER_ITEMS.get(i));
            }

            List<AdAccountDto.Response.ForAgencySearch> content = this.adAccountRepository.searchForAgency(
                    new AdAccountDto.Request.ForAgencySearch(), SecurityUtils.getLoginUserId());

            int rowNum = 1;
            for (AdAccountDto.Response.ForAgencySearch data : content) {
                XSSFRow row = sheet.createRow(rowNum++);

                int columnNum = 0;
                XSSFCell cell = row.createCell(columnNum++);
                cell.setCellValue(data.getId());

                XSSFCell cell1 = row.createCell(columnNum++);
                cell1.setCellValue(data.getName());

                XSSFCell cell2 = row.createCell(columnNum++);
                cell2.setCellValue(this.getStatus(data.getConfig(), data.isAdminStop(), data.isOutOfBalance()));

                XSSFCell cell3 = row.createCell(columnNum++);
                cell3.setCellValue(data.getMarketerName());

                XSSFCell cell4 = row.createCell(columnNum++);
                cell4.setCellValue(data.getCreditLimit());

                XSSFCell cell5 = row.createCell(columnNum);
                cell5.setCellValue(data.getWalletSpend().getCash());
            }

            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("adaccount-paybalance-list.xlsx").build());
        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
        headers.setContentType(new MediaType("application", "vnd.ms-excel"));
        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }

    private String getStatus(AdAccount.Config config, boolean adminStop, boolean outOfBalance) {
        if (adminStop) return "관리자정지";
        switch (config) {
            case ON:
                return outOfBalance ? "잔액부족" : "운영중";
            case OFF:
                return outOfBalance ? "사용자OFF,잔액부족" : "사용자OFF";
            case DEL:
                return "-";
        }
        return null;
    }

    private String getCompanyType(Company.Type companyType) {
        switch (companyType) {
            case AGENCY:
                return "사업자(대행사)";
            case ADVERTISER:
                return "사업자(광고주)";
        }
        return null;
    }
}
