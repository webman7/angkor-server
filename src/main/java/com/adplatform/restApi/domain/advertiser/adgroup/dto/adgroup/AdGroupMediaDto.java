package com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

public class AdGroupMediaDto {

    public static abstract class Response {
        @Getter
        @Setter
        public static class Default {
            private Integer adgroupId;
            private Integer mediaId;

            @QueryProjection
            public Default(
                    Integer adgroupId,
                    Integer mediaId) {
                this.adgroupId = adgroupId;
                this.mediaId = mediaId;
            }
        }
    }
}
