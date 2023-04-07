package com.item.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "item")
public class ItemAppProperties {

    private List listing = new List();

    @Data
    public static class List {

        private DefaultListing defaults = new DefaultListing();

        @Data
        public static class DefaultListing {
            private Integer limit = 10;
            private Integer offset = 0;
        }

    }

}
