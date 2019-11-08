package com.opencoinfolium.webapi.entity

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Coin Basic Data object")
class CoinData(
        @ApiModelProperty(notes = "Coin Id")
        var id: Long,
        @ApiModelProperty(notes = "Coin name")
        var name: String,
        @ApiModelProperty(notes = "Coin symbol")
        var symbol: String,
        @ApiModelProperty(notes = "Coin slug")
        var slug: String,
        @ApiModelProperty(notes = "Coin circulating supply")
        var circulatingSupply: Long?,
        @ApiModelProperty(notes = "Coin total supply")
        var totalSupply: Long?,
        @ApiModelProperty(notes = "Coin max supply")
        var maxSupply: Long?,
        @ApiModelProperty(notes = "Coin image url")
        var url: String? = null
) {
    override fun toString(): String {
        return "CoinData(id=$id, name='$name', symbol='$symbol', slug='$slug', " +
                "circulatingSupply=$circulatingSupply, totalSupply=$totalSupply, maxSupply=$maxSupply, url=$url)"
    }
}