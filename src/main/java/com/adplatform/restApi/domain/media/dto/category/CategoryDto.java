package com.adplatform.restApi.domain.media.dto.category;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            @Size(min = 1, max = 50)
            @NotBlank
            private String name;
        }

        @Getter
        @Setter
        public static class Update {

            private Integer id;

            @Size(min = 1, max = 50)
            @NotBlank
            private String name;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class BankInfo {

            private Integer id;
            private String name;
        }


        @Getter
        @Setter
        public static class CategoryInfo {

            private Integer id;
            private String name;

            @QueryProjection
            public CategoryInfo(Integer id, String name) {
                this.id = id;
                this.name = name;
            }
        }


    }
}
