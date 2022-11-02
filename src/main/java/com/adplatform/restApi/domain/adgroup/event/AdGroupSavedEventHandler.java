package com.adplatform.restApi.domain.adgroup.event;

import com.adplatform.restApi.domain.adgroup.service.AdGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdGroupSavedEventHandler {
    private final AdGroupService adGroupService;

    @EventListener(AdGroupSavedEvent.class)
    public void handle(AdGroupSavedEvent event) {
        this.adGroupService.save(event);
    }
}
