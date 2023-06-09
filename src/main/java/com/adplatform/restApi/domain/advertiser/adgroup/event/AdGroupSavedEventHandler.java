package com.adplatform.restApi.domain.advertiser.adgroup.event;

import com.adplatform.restApi.domain.advertiser.adgroup.service.AdGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class AdGroupSavedEventHandler {
    private final AdGroupService adGroupService;

    @EventListener(AdGroupSavedEvent.class)
    public void handle(AdGroupSavedEvent event) {
        this.adGroupService.save(event);
    }
}
