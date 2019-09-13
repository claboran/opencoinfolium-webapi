package com.opencoinfolium.webapi.entity

import java.time.OffsetDateTime

class Quote(
        var coinId: Long = 0,
        var price: Double,
        var volume24h: Double,
        var marketCap: Double,
        var percentageChange1h: Double,
        var percentageChange24h: Double,
        var percentageChange7d: Double,
        var lastUpdate: OffsetDateTime
)