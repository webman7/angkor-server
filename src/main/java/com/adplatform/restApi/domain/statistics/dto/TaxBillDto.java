package com.adplatform.restApi.domain.statistics.dto;

import com.adplatform.restApi.domain.bank.domain.Bank;
import com.adplatform.restApi.domain.media.dto.MediaFileDto;
import com.adplatform.restApi.domain.statistics.domain.taxbill.FileInformation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaxBillDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private Integer statDate;
            private Integer companyId;
            private Integer mediaId;
            private List<MultipartFile> mediaTaxBillFiles = new ArrayList<>();
            private String memo;
            private Float supplyAmount;
            private Float vatAmount;
            private Float totalAmount;
        }

        @Getter
        @Setter
        public static class Update extends TaxBillDto.Request.Save {
            @NotNull
            private Integer id;
        }

        @Getter
        @Setter
        public static class Confirm {
            @NotNull
            private Integer id;
        }

        @Getter
        @Setter
        public static class Payment {
            @NotNull
            private Integer id;
            private Integer bankId;
            private String accountNumber;
            private String accountOwner;
            private Integer paymentDate;
            private List<MultipartFile> mediaTaxBillPaymentFiles = new ArrayList<>();
            private String adminMemo;
        }

        @Getter
        @Setter
        public static class SearchTax {
            private Integer companyId;
            private String companyName;
            private String mediaName;
            private String startDate;
            private String endDate;
        }
    }

    public static class Response {
        @Getter
        @Setter
        public static class TaxBill {
            private Integer id;
            private Integer mediaId;
            private String mediaName;
            private Integer companyId;
            private String companyName;
            private Integer statDate;
            private Float supplyAmount;
            private Float vatAmount;
            private Float totalAmount;
            private String memo;
            private String mediaTaxBillFileUrl;
            private String mediaTaxBillFileName;
            private FileInformation.FileType mediaTaxBillFileType;
            private boolean issueStatus;
            private Integer issueUserNo;
            private String issueUserId;
            private LocalDateTime issueAt;
            private boolean paymentStatus;
            private Integer paymentUserNo;
            private String paymentUserId;
            private LocalDateTime paymentAt;

            @QueryProjection
            public TaxBill(
                    Integer id,
                    Integer mediaId,
                    String mediaName,
                    Integer companyId,
                    String companyName,
                    Integer statDate,
                    Float supplyAmount,
                    Float vatAmount,
                    Float totalAmount,
                    String memo,
                    String mediaTaxBillFileUrl,
                    String mediaTaxBillFileName,
                    FileInformation.FileType mediaTaxBillFileType,
                    Boolean issueStatus,
                    Integer issueUserNo,
                    String issueUserId,
                    LocalDateTime issueAt,
                    Boolean paymentStatus,
                    Integer paymentUserNo,
                    String paymentUserId,
                    LocalDateTime paymentAt) {
                this.id = id;
                this.mediaId = mediaId;
                this.mediaName = mediaName;
                this.companyId = companyId;
                this.companyName = companyName;
                this.statDate = statDate;
                this.supplyAmount = supplyAmount;
                this.vatAmount = vatAmount;
                this.totalAmount = totalAmount;
                this.memo = memo;
                this.mediaTaxBillFileUrl = mediaTaxBillFileUrl;
                this.mediaTaxBillFileName = mediaTaxBillFileName;
                this.mediaTaxBillFileType = mediaTaxBillFileType;
                this.issueStatus = issueStatus;
                this.issueUserNo = issueUserNo;
                this.issueUserId = issueUserId;
                this.issueAt = issueAt;
                this.paymentStatus = paymentStatus;
                this.paymentUserNo = paymentUserNo;
                this.paymentUserId = paymentUserId;
                this.paymentAt = paymentAt;
            }
        }

        @Getter
        @Setter
        public static class TaxBillInfo {
            private Integer id;
            private Integer mediaId;
            private String mediaName;
            private Integer companyId;
            private String companyName;
            private Integer statDate;
            private Float supplyAmount;
            private Float vatAmount;
            private Float totalAmount;
            private String memo;
            private MediaTaxBillFileDto.Response.FileInfo mediaTaxBillFiles;
            private MediaTaxBillPaymentFileDto.Response.FileInfo mediaTaxBillPaymentFiles;
            private boolean issueStatus;
            private Integer issueUserNo;
            private LocalDateTime issueAt;
            private Bank bank;
            private String accountNumber;
            private String accountOwner;

            private boolean paymentStatus;
            private Integer paymentUserNo;
            private LocalDateTime paymentAt;
        }
    }
}
