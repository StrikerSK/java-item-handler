package com.item.app;

import com.item.app.config.ItemAppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@ConfigurationPropertiesScan("com.item.app.config")
public class ItemsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemsAppApplication.class, args);
    }

}
