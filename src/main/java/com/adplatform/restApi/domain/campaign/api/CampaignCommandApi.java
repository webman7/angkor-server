package com.adplatform.restApi.domain.campaign.api;

import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.campaign.service.CampaignSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/campaign")
public class CampaignCommandApi {
    private final CampaignSaveService campaignSaveService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid CampaignDto.Request.Save request) {
        campaignSaveService.save(request);
        return ResponseEntity.ok().build();
    }
}
