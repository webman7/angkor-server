package com.adplatform.restApi.agency.businessright.service;

import com.adplatform.restApi.advertiser.company.service.CompanyFindUtils;
import com.adplatform.restApi.advertiser.statistics.dto.SaleAmountDto;
import com.adplatform.restApi.advertiser.wallet.dto.WalletDto;
import com.adplatform.restApi.agency.businessright.dao.BusinessRightRepository;
import com.adplatform.restApi.agency.businessright.dao.BusinessRightRequestRepository;
import com.adplatform.restApi.agency.businessright.domain.BusinessRight;
import com.adplatform.restApi.agency.businessright.domain.BusinessRightRequest;
import com.adplatform.restApi.agency.businessright.dto.BusinessRightDto;
import com.adplatform.restApi.agency.businessright.dto.BusinessRightMapper;
import com.adplatform.restApi.agency.businessright.dto.BusinessRightRequestMapper;
import com.adplatform.restApi.batch.dto.BatchStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BusinessRightService {

    private final BusinessRightRequestRepository businessRightRequestRepository;
    private final BusinessRightRepository businessRightRepository;
    private final BusinessRightRequestMapper businessRightRequestMapper;
    private final BusinessRightMapper businessRightMapper;

    public void saveBusinessRightRequest(BusinessRightDto.Request.SaveRequest request) {
        BusinessRightRequest businessRightRequest = this.businessRightRequestMapper.toEntity(request);
        this.businessRightRequestRepository.save(businessRightRequest);
    }

    public void saveBusinessRightStatus(BusinessRightDto.Request.SaveStatus request) {
        if(request.getStatus().equals("COMPLETED") || request.getStatus().equals("TRANSFERRED")) {
            BusinessRightRequest businessRightRequest = this.businessRightRequestRepository.findById(request.getId()).orElseThrow();
            if(businessRightRequest.getStatus().equals(BusinessRightRequest.Status.valueOf("REQUESTED"))) {
                Calendar calendar = new GregorianCalendar();
                SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
                String chkDate = SDF.format(calendar.getTime());
                Integer startDate = Integer.parseInt(chkDate);

                // 기존 데이터 종료 시킴
                List<BusinessRightDto.Response.BusinessRightDetail> list = this.businessRightRepository.getBusinessRight(businessRightRequest.getAdAccountId(), businessRightRequest.getCompanyId());
                if(list.size() > 0) {
                    Calendar calendar2 = new GregorianCalendar();
                    SimpleDateFormat SDF2 = new SimpleDateFormat("yyyyMMdd");
                    calendar2.add(Calendar.DATE, -1);
                    String chkDate2 = SDF2.format(calendar2.getTime());
                    Integer endDate = Integer.parseInt(chkDate2);

                    for (BusinessRightDto.Response.BusinessRightDetail wa: list) {
                        BusinessRightDto.Request.Save businessRightList = new BusinessRightDto.Request.Save();
                        businessRightList.setEndDate(endDate);

                        BusinessRightFindUtils.findByIdOrElseThrow(wa.getId(), this.businessRightRepository).update(businessRightList);
                    }
                }
                // 기존 데이터 입력 시킴
                BusinessRightDto.Request.Save businessRightList = new BusinessRightDto.Request.Save();
                businessRightList.setAdAccountId(businessRightRequest.getAdAccountId());
                businessRightList.setCompanyId(businessRightRequest.getRequestCompanyId());
                businessRightList.setStartDate(startDate);
                businessRightList.setEndDate(29991231);

                BusinessRight businessRight = this.businessRightMapper.toEntity(businessRightList);
                this.businessRightRepository.save(businessRight);
            }

        }
        BusinessRightRequestFindUtils.findByIdOrElseThrow(request.getId(), this.businessRightRequestRepository).update(request);

    }
}
