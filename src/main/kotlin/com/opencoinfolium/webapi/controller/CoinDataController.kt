package com.opencoinfolium.webapi.controller

import com.opencoinfolium.webapi.entity.CoinData
import com.opencoinfolium.webapi.loggingutils.LoggingAware
import com.opencoinfolium.webapi.repository.ICoinDataRepo
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


/**
 * Basic data controller delivers complete set of CoinData
 *
 * @author christian@laboranowitsch.de
 */
@RestController
@RequestMapping("/api/v1/basicdata")
@Api(value = "Basic Data Endpoint", description = "Delivers Coin Basic-Data")
class CoinDataController @Autowired constructor(
        val coinDataRepo: ICoinDataRepo
) : LoggingAware {

    @ApiOperation(value = "Delivers coin data", response = CoinData::class, responseContainer = "List")
    @ApiResponses(value = [ApiResponse(code = 200, message = "Successfully delivered Coin Data")])
    @RequestMapping(value = ["/coins"], method = [RequestMethod.GET])
    fun getAll(): ResponseEntity<List<CoinData>> = ResponseEntity.ok(coinDataRepo.findAll())
}