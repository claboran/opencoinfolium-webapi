package com.opencoinfolium.webapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class WebapiApplication

fun main(args: Array<String>) {
    runApplication<WebapiApplication>(*args)
}
