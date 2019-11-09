package com.opencoinfolium.webapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * @see <a href="http://www.javaguides.net/2018/10/spring-boot-2-restful-api-documentation-with-swagger2-tutorial.html>Doku</a>
 */
@Configuration
@EnableSwagger2
@Profile(value = [RunProfile.LOCAL_DEV, RunProfile.LOCAL_DEV_H2])
class SwaggerConfig {

    @Bean
    fun apiDocket(): Docket = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.opencoinfolium.webapi"))
            .paths(PathSelectors.any())
            .build()
}