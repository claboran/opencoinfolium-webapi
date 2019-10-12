package com.opencoinfolium.webapi.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.client.RestTemplate


@Configuration
@Profile(value = [RunProfile.INT_TEST, RunProfile.LOCAL_DEV])
class Config {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()
}