package com.booking.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.booking.dto.Suite;

@Configuration
@EnableWebMvc
public class AppConfig {

    @Bean
    public Map<String, Suite> strategies(List<Suite> strategiesList) {
        Map<String, Suite> strategiesMap = new HashMap<>();
        for (Suite strategy : strategiesList) {
            Qualifier qualifier = strategy.getClass().getAnnotation(Qualifier.class);
            if (qualifier != null) {
                String strategyName = qualifier.value();
                strategiesMap.put(strategyName, strategy);
            }
        }
        return strategiesMap;
    }

}