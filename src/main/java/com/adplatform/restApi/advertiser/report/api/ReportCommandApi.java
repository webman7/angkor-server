package com.adplatform.restApi.advertiser.report.api;

import com.adplatform.restApi.advertiser.report.dto.custom.ReportCustomDto;
import com.adplatform.restApi.advertiser.report.service.ReportSaveService;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportCommandApi {

    private final ReportSaveService reportSaveService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/custom")
    public void save(@RequestBody @Valid ReportCustomDto.Request.Save request) {
        this.reportSaveService.save(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/custom")
    public ResponseEntity<Void> update(@RequestBody @Valid ReportCustomDto.Request.Update request) {
        this.reportSaveService.update(request);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/custom/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.reportSaveService.delete(id);
        return ResponseEntity.ok().build();
    }

}
