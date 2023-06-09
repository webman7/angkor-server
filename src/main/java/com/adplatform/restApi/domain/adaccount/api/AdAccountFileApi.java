package com.adplatform.restApi.domain.adaccount.api;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.util.ExcelUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.CellStyle;
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
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/adaccounts/download")
public class AdAccountFileApi {
    private final AdAccountRepository adAccountRepository;

    private static final List<String> HEADER_MY_ITEMS = List.of(
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

    private static final List<String> HEADER_PAYBALANCE_ITEMS = List.of(
            "광고계정 ID",
            "광고계정",
            "상태",
            "마케터명",
            "후불한도",
            "유상캐시 잔액");
    private static final List<String> HEADER_CASH_ITEMS = List.of(
            "광고계정 ID",
            "광고계정",
            "상태",
            "유상캐시 잔액",
            "무상캐시 잔액");

//    @SneakyThrows
//    @GetMapping("/adaccount/csv")
//    public ResponseEntity<byte[]> downloadInfoCsv(AdAccountDto.Request.ForAdminSearch request) {
//        List<AdAccountDto.Response.ForAdminSearch> content = this.adAccountRepository.searchForAdmin(
//                request);
//        StringBuilder sb = new StringBuilder();
//        sb.append(String.join(",", HEADER_MY_ITEMS)).append("\n");
//        content.forEach(c -> sb.append(
//                String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
//                        c.getId(),
//                        c.getName(),
//                        this.getStatus(c.getConfig(), c.isAdminStop()),
////                        this.getCompanyType(c.getCompanyType()),
////                        c.isPrePayment() ? "후불" : "선불",
////                        c.getCreditLimit(),
//                        "","","",
//                        c.getWalletSpend().getCash(),
//                        c.getWalletSpend().getTodaySpend(),
//                        c.getWalletSpend().getYesterdaySpend(),
//                        c.getWalletSpend().getMonthSpend())
//        ));
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        baos.write(0xEF);
//        baos.write(0xBB);
//        baos.write(0xBF);
//        baos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=adaccount-info-list.csv")
//                .contentType(new MediaType("text", "csv"))
//                .body(baos.toByteArray());
//    }
//
//    @SneakyThrows
//    @GetMapping("/adaccount/excel")
//    public ResponseEntity<byte[]> downloadInfoExcel(AdAccountDto.Request.ForAdminSearch request) {
//        ByteArrayOutputStream baos;
//        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
//            XSSFSheet sheet = workbook.createSheet("adaccount");
//            XSSFCell cell = null;
//            XSSFRow row = null;
//
//            //테이블 헤더 스타일 적용
//            CellStyle headerStyle = ExcelUtils.CellStyleSetting(workbook, "header");
//            //테이블 데이터 스타일 적용
//            CellStyle dataStyle = ExcelUtils.CellStyleSetting(workbook, "data");
//
//            XSSFRow headerRow = sheet.createRow(0);
//            for (int i = 0; i < HEADER_MY_ITEMS.size(); i++) {
//                cell = headerRow.createCell(i);
//                cell.setCellValue(HEADER_MY_ITEMS.get(i));
//                cell.setCellStyle(headerStyle);
//            }
//
//            List<AdAccountDto.Response.ForAdminSearch> content = this.adAccountRepository.searchForAdmin(
//                    request);
//
//            int rowNum = 1;
//            for (AdAccountDto.Response.ForAdminSearch data : content) {
//                row = sheet.createRow(rowNum++);
//
//                int columnNum = 0;
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getId());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getName());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(this.getStatus(data.getConfig(), data.isAdminStop()));
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
////                cell.setCellValue(this.getCompanyType(data.getCompanyType()));
//                cell.setCellValue("");
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
////                cell.setCellValue(data.isPrePayment() ? "후불" : "선불");
//                cell.setCellValue("");
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
////                cell.setCellValue(data.getCreditLimit());
//                cell.setCellValue("");
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getWalletSpend().getCash());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getWalletSpend().getTodaySpend());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getWalletSpend().getYesterdaySpend());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum);
//                cell.setCellValue(data.getWalletSpend().getMonthSpend());
//                cell.setCellStyle(dataStyle);
//            }
//
//            //셀 넓이 자동 조정
//            for (int i = 0; i < HEADER_MY_ITEMS.size(); i++) {
//                sheet.autoSizeColumn(i);
//                sheet.setColumnWidth(i, sheet.getColumnWidth(i));
//            }
//
//            baos = new ByteArrayOutputStream();
//            workbook.write(baos);
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDisposition(ContentDisposition.attachment().filename("adaccount-info-list.xlsx").build());
//        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
//        headers.setContentType(new MediaType("application", "vnd.ms-excel"));
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(baos.toByteArray());
//    }
//
//    @SneakyThrows
//    @GetMapping("/my/csv")
//    public ResponseEntity<byte[]> downloadMyCsv(AdAccountDto.Request.ForAgencySearch request) {
//        List<AdAccountDto.Response.ForAgencySearch> content = this.adAccountRepository.searchForAgency(
//                request, SecurityUtils.getLoginUserNo());
//        StringBuilder sb = new StringBuilder();
//        sb.append(String.join(",", HEADER_MY_ITEMS)).append("\n");
//        content.forEach(c -> sb.append(
//                String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
//                        c.getId(),
//                        c.getName(),
//                        this.getStatus(c.getConfig(), c.isAdminStop()),
////                        this.getCompanyType(c.getCompanyType()),
////                        c.isPrePayment() ? "후불" : "선불",
////                        c.getCreditLimit(),
//                        "","","",
//                        c.getWalletSpend().getCash(),
//                        c.getWalletSpend().getTodaySpend(),
//                        c.getWalletSpend().getYesterdaySpend(),
//                        c.getWalletSpend().getMonthSpend())
//        ));
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        baos.write(0xEF);
//        baos.write(0xBB);
//        baos.write(0xBF);
//        baos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=adaccount-my-list.csv")
//                .contentType(new MediaType("text", "csv"))
//                .body(baos.toByteArray());
//    }
//
//    @SneakyThrows
//    @GetMapping("/my/excel")
//    public ResponseEntity<byte[]> downloadMyExcel(AdAccountDto.Request.ForAgencySearch request) {
//        ByteArrayOutputStream baos;
//        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
//            XSSFSheet sheet = workbook.createSheet("adaccount");
//            XSSFCell cell = null;
//            XSSFRow row = null;
//
//            //테이블 헤더 스타일 적용
//            CellStyle headerStyle = ExcelUtils.CellStyleSetting(workbook, "header");
//            //테이블 데이터 스타일 적용
//            CellStyle dataStyle = ExcelUtils.CellStyleSetting(workbook, "data");
//
//            XSSFRow headerRow = sheet.createRow(0);
//            for (int i = 0; i < HEADER_MY_ITEMS.size(); i++) {
//                cell = headerRow.createCell(i);
//                cell.setCellValue(HEADER_MY_ITEMS.get(i));
//                cell.setCellStyle(headerStyle);
//            }
//
//            List<AdAccountDto.Response.ForAgencySearch> content = this.adAccountRepository.searchForAgency(
//                    request, SecurityUtils.getLoginUserNo());
//
//            int rowNum = 1;
//            for (AdAccountDto.Response.ForAgencySearch data : content) {
//                row = sheet.createRow(rowNum++);
//
//                int columnNum = 0;
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getId());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getName());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(this.getStatus(data.getConfig(), data.isAdminStop()));
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
////                cell.setCellValue(this.getCompanyType(data.getCompanyType()));
//                cell.setCellValue("");
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
////                cell.setCellValue(data.isPrePayment() ? "후불" : "선불");
//                cell.setCellValue("");
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
////                cell.setCellValue(data.getCreditLimit());
//                cell.setCellValue("");
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getWalletSpend().getCash());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getWalletSpend().getTodaySpend());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getWalletSpend().getYesterdaySpend());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum);
//                cell.setCellValue(data.getWalletSpend().getMonthSpend());
//                cell.setCellStyle(dataStyle);
//            }
//
//            //셀 넓이 자동 조정
//            for (int i = 0; i < HEADER_MY_ITEMS.size(); i++) {
//                sheet.autoSizeColumn(i);
//                sheet.setColumnWidth(i, sheet.getColumnWidth(i));
//            }
//
//            baos = new ByteArrayOutputStream();
//            workbook.write(baos);
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDisposition(ContentDisposition.attachment().filename("adaccount-my-list.xlsx").build());
//        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
//        headers.setContentType(new MediaType("application", "vnd.ms-excel"));
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(baos.toByteArray());
//    }

    @SneakyThrows
    @GetMapping("/cash/csv")
    public ResponseEntity<byte[]> downloadCashCsv(AdAccountDto.Request.ForCashSearch request) {
        List<AdAccountDto.Response.ForCashSearch> content = this.adAccountRepository.searchForCash(
                request);
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", HEADER_CASH_ITEMS)).append("\n");
        content.forEach(c -> sb.append(
                String.format("%s,%s,%s,%s,%s\n",
                        c.getId(),
                        c.getName(),
                        this.getStatus(c.getConfig(), c.isAdminStop()),
//                        c.getWalletBalance().getCash(),
//                        c.getWalletBalance().getFreeCash()
                        0, 0
                )
        ));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0xEF);
        baos.write(0xBB);
        baos.write(0xBF);
        baos.write(sb.toString().getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=adaccount-cash-list.csv")
                .contentType(new MediaType("text", "csv"))
                .body(baos.toByteArray());
    }

    @SneakyThrows
    @GetMapping("/cash/excel")
    public ResponseEntity<byte[]> downloadCashExcel(AdAccountDto.Request.ForCashSearch request) {
        ByteArrayOutputStream baos;
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("adaccount");
            XSSFCell cell = null;
            XSSFRow row = null;

            //테이블 헤더 스타일 적용
            CellStyle headerStyle = ExcelUtils.CellStyleSetting(workbook, "header");
            //테이블 데이터 스타일 적용
            CellStyle dataStyle = ExcelUtils.CellStyleSetting(workbook, "data");

            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADER_CASH_ITEMS.size(); i++) {
                cell = headerRow.createCell(i);
                cell.setCellValue(HEADER_CASH_ITEMS.get(i));
                cell.setCellStyle(headerStyle);
            }

            List<AdAccountDto.Response.ForCashSearch> content = this.adAccountRepository.searchForCash(
                    request);

            int rowNum = 1;
            for (AdAccountDto.Response.ForCashSearch data : content) {
                row = sheet.createRow(rowNum++);

                int columnNum = 0;
                cell = row.createCell(columnNum++);
                cell.setCellValue(data.getId());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(columnNum++);
                cell.setCellValue(data.getName());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(columnNum++);
                cell.setCellValue(this.getStatus(data.getConfig(), data.isAdminStop()));
                cell.setCellStyle(dataStyle);

                cell = row.createCell(columnNum);
//                cell.setCellValue(data.getWalletBalance().getCash());
                cell.setCellValue(0);
                cell.setCellStyle(dataStyle);

                cell = row.createCell(columnNum);
//                cell.setCellValue(data.getWalletBalance().getFreeCash());
                cell.setCellValue(0);
                cell.setCellStyle(dataStyle);
            }

            //셀 넓이 자동 조정
            for (int i = 0; i < HEADER_CASH_ITEMS.size(); i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, sheet.getColumnWidth(i));
            }

            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("adaccount-cash-list.xlsx").build());
        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
        headers.setContentType(new MediaType("application", "vnd.ms-excel"));
        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }

//    @SneakyThrows
//    @GetMapping("/paybalance/csv")
//    public ResponseEntity<byte[]> downloadPayBalanceCsv(AdAccountDto.Request.ForAgencySearch request) {
//        List<AdAccountDto.Response.ForAgencySearch> content = this.adAccountRepository.searchForAgency(
//                request, SecurityUtils.getLoginUserNo());
//        StringBuilder sb = new StringBuilder();
//        sb.append(String.join(",", HEADER_PAYBALANCE_ITEMS)).append("\n");
//        content.forEach(c -> sb.append(
//                String.format("%s,%s,%s,%s,%s,%s\n",
//                        c.getId(),
//                        c.getName(),
//                        this.getStatus(c.getConfig(), c.isAdminStop()),
//                        c.getMarketerName(),
////                        c.getCreditLimit(),
//                        0,
//                        c.getWalletSpend().getCash())
//        ));
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        baos.write(0xEF);
//        baos.write(0xBB);
//        baos.write(0xBF);
//        baos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=adaccount-paybalance-list.csv")
//                .contentType(new MediaType("text", "csv"))
//                .body(baos.toByteArray());
//    }
//
//    @SneakyThrows
//    @GetMapping("/paybalance/excel")
//    public ResponseEntity<byte[]> downloadPayBalanceExcel(AdAccountDto.Request.ForAgencySearch request) {
//        ByteArrayOutputStream baos;
//        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
//            XSSFSheet sheet = workbook.createSheet("adaccount");
//            XSSFCell cell = null;
//            XSSFRow row = null;
//
//            //테이블 헤더 스타일 적용
//            CellStyle headerStyle = ExcelUtils.CellStyleSetting(workbook, "header");
//            //테이블 데이터 스타일 적용
//            CellStyle dataStyle = ExcelUtils.CellStyleSetting(workbook, "data");
//
//            XSSFRow headerRow = sheet.createRow(0);
//            for (int i = 0; i < HEADER_PAYBALANCE_ITEMS.size(); i++) {
//                cell = headerRow.createCell(i);
//                cell.setCellValue(HEADER_PAYBALANCE_ITEMS.get(i));
//                cell.setCellStyle(headerStyle);
//            }
//
//            List<AdAccountDto.Response.ForAgencySearch> content = this.adAccountRepository.searchForAgency(
//                    request, SecurityUtils.getLoginUserNo());
//
//            int rowNum = 1;
//            for (AdAccountDto.Response.ForAgencySearch data : content) {
//                row = sheet.createRow(rowNum++);
//
//                int columnNum = 0;
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getId());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getName());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(this.getStatus(data.getConfig(), data.isAdminStop()));
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
//                cell.setCellValue(data.getMarketerName());
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum++);
////                cell.setCellValue(data.getCreditLimit());
//                cell.setCellValue(0);
//                cell.setCellStyle(dataStyle);
//
//                cell = row.createCell(columnNum);
//                cell.setCellValue(data.getWalletSpend().getCash());
//                cell.setCellStyle(dataStyle);
//            }
//
//            //셀 넓이 자동 조정
//            for (int i = 0; i < HEADER_PAYBALANCE_ITEMS.size(); i++) {
//                sheet.autoSizeColumn(i);
//                sheet.setColumnWidth(i, sheet.getColumnWidth(i));
//            }
//
//            baos = new ByteArrayOutputStream();
//            workbook.write(baos);
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDisposition(ContentDisposition.attachment().filename("adaccount-paybalance-list.xlsx").build());
//        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
//        headers.setContentType(new MediaType("application", "vnd.ms-excel"));
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(baos.toByteArray());
//    }

    private String getStatus(AdAccount.Config config, boolean adminStop) {
        if (adminStop) return "관리자정지";
        switch (config) {
            case ON:
                return "운영중";
            case OFF:
                return "사용자OFF";
            case DEL:
                return "-";
        }
        return null;
    }

//    private String getCompanyType(Company.Type companyType) {
//        switch (companyType) {
//            case AGENCY:
//                return "대행사";
//            case ADVERTISER:
//                return "광고주";
//        }
//        return null;
//    }
}
