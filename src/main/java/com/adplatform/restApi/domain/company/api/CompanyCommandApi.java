package com.adplatform.restApi.domain.company.api;

import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyCommandApi {
    private final CompanyService companyService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/advertisers")
    public void saveAdvertiser(@RequestBody @Valid CompanyDto.Request.Save request) {
        this.companyService.saveAdvertiser(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/agencies")
    public void saveAgency(@RequestBody @Valid CompanyDto.Request.Save request) {
        this.companyService.saveAgency(request);
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody @Valid CompanyDto.Request.Update request) {
        this.companyService.update(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.companyService.delete(id);
        return ResponseEntity.ok().build();
    }
}
