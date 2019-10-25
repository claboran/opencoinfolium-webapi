package com.opencoinfolium.webapi.client.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data classes to deserialize incoming Json.
 *
 * @author christian@laboranowitsch.de
 */

data class QuoteItem(
    var price: Double,
    var volume_24h: Double,
    var market_cap: Double,
    var percentage_change_1h: Double,
    var percentage_change_24h: Double,
    var percentage_change_7d: Double
)

data class CurrencyItem(
    @JsonProperty("USD")
    var USD: QuoteItem
)

data class CoinItem(
        var id: Long,
        var name: String,
        var symbol: String,
        var website_slug: String,
        var rank: Long,
        var circulating_supply: Long,
        var total_supply: Long,
        var max_supply: Long,
        var quotes: CurrencyItem,
        var last_updated: Long
)
data class MetaData (
        var timestamp: String,
        var num_cryptocurrencies: Long,
        var error: String?
)

data class Ticker(
        var data: List<CoinItem>,
        var metadata: MetaData
)