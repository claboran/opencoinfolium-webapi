package com.opencoinfolium.webapi.entity

class CoinData(
        var id: Long,
        var name: String,
        var symbol: String,
        var slug: String,
        var circulatingSupply: Long?,
        var totalSupply: Long?,
        var maxSupply: Long?,
        var url: String? = null
) {
    override fun toString(): String {
        return "CoinData(id=$id, name='$name', symbol='$symbol', slug='$slug', " +
                "circulatingSupply=$circulatingSupply, totalSupply=$totalSupply, maxSupply=$maxSupply, url=$url)"
    }
}