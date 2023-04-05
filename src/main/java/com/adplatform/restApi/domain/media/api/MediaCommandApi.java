package com.adplatform.restApi.domain.media.api;

import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.service.MediaSaveService;
import com.adplatform.restApi.domain.statistics.dto.TaxBillDto;
import com.adplatform.restApi.domain.statistics.service.MediaTaxBillSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaCommandApi {

    private final MediaSaveService mediaSaveService;
    private final MediaTaxBillSaveService mediaTaxBillSaveService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@Valid MediaDto.Request.Save request) {
        this.mediaSaveService.save(request);
    }

    @PatchMapping
    public ResponseEntity<Void> update(@Valid MediaDto.Request.Update request) {
        this.mediaSaveService.update(request);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/admin")
    public void saveAdmin(@Valid MediaDto.Request.Save request) {
        this.mediaSaveService.saveAdmin(request);
    }

    @PatchMapping("/admin")
    public ResponseEntity<Void> updateAdmin(@Valid MediaDto.Request.Update request) {
        this.mediaSaveService.updateAdmin(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/approve")
    public ResponseEntity<Void> updateAdminApprove(@RequestBody @Valid MediaDto.Request.Confirm request) {
        this.mediaSaveService.updateAdminApprove(request);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/admin/reject")
    public ResponseEntity<Void> updateAdminReject(@RequestBody @Valid MediaDto.Request.Confirm request) {
        this.mediaSaveService.updateAdminReject(request);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/admin/delete")
    public ResponseEntity<Void> updateAdminDelete(@RequestBody @Valid MediaDto.Request.Confirm request) {
        this.mediaSaveService.updateAdminDelete(request);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.mediaSaveService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/tax/bill")
    public void saveMediaTaxBill(@Valid TaxBillDto.Request.Save request) {
        this.mediaTaxBillSaveService.saveMediaTaxBill(request);
    }

    @PatchMapping("/tax/bill")
    public ResponseEntity<Void> updateMediaTaxBill(@Valid TaxBillDto.Request.Update request) {
        this.mediaTaxBillSaveService.updateMediaTaxBill(request);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/tax/bill/issue")
    public ResponseEntity<Void> updateMediaTaxBillIssue(@RequestBody @Valid TaxBillDto.Request.Confirm request) {
        this.mediaTaxBillSaveService.updateMediaTaxBillIssue(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/tax/bill/payment")
    public ResponseEntity<Void> updateMediaTaxBillPayment(@Valid TaxBillDto.Request.Payment request) {
        this.mediaTaxBillSaveService.updateMediaTaxBillPayment(request);
        return ResponseEntity.ok().build();
    }
}
