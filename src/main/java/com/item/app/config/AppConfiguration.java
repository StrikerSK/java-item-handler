package com.item.app.config;

import com.item.app.utils.ListingFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ListingFactory listingFactory(ItemAppProperties properties) {
        Integer defaultLimit = properties.getListing().getDefaults().getLimit();
        Integer defaultOffset = properties.getListing().getDefaults().getOffset();
        return new ListingFactory(defaultLimit, defaultOffset);
    }

}
