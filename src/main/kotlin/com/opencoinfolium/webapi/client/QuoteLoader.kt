package com.opencoinfolium.webapi.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.opencoinfolium.webapi.client.model.QuoteItems
import com.opencoinfolium.webapi.client.model.Ticker
import com.opencoinfolium.webapi.loggingutils.LoggingAware
import com.opencoinfolium.webapi.loggingutils.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

fun convertFromJson(jsonData: String): Ticker = ObjectMapper()
        .registerModule(KotlinModule())
        .readValue(jsonData)

interface IQuoteLoader {
    fun loadQuotes(): List<QuoteItems>
}

@Component
class QuoteLoader @Autowired constructor(
        val restTemplate: RestTemplate,
        @Value("\${importer.client.tickerurl}") val tickerUrl: String
): IQuoteLoader, LoggingAware{

    override fun loadQuotes(): List<QuoteItems> =
        convertFromJson(restTemplate
                .exchange(
                        tickerUrl,
                        HttpMethod.GET,
                        null,
                        String::class.java)
                .body!!).data.also { logger().info(it.toString()) }

}