package com.opencoinfolium.webapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.opencoinfolium.webapi.entity.CoinData
import com.opencoinfolium.webapi.loggingutils.LoggingAware
import com.opencoinfolium.webapi.repository.CoinDataRepo
import com.opencoinfolium.webapi.testutils.DbLoader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

private  data class CoinDataRes(
        var id: Int,
        var name: String,
        var symbol: String,
        var slug: String,
        var circulatingSupply: Long,
        var totalSupply: Long,
        var maxSupply: Long,
        var url: String?
)

private fun convertFromJson(jsonData: String): List<CoinDataRes> = ObjectMapper()
        .registerModule(KotlinModule())
        .readValue(jsonData)

/**
 * Controller integration test for {@link CoinDataController}.
 *
 * @author christian@laboranowitsch.de
 *
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoinDataControllerTests @Autowired constructor(
        val coinDataController: CoinDataController,
        val dbLoader: DbLoader,
        val coinDataRepo: CoinDataRepo,
        val testRestTemplate: TestRestTemplate
        ) : LoggingAware {


    @BeforeEach
    fun contextLoads() {
        dbLoader.prepareDatabase()
        populateDb()
    }

    @Test
    fun `Assert that we successfully can get the basic data `() {
        val res = testRestTemplate
                .getForEntity(
                        "/api/v1/basicdata/coins",
                        String::class.java
                ).also { assertThat(it.statusCode)
                        .`as`("has http status ok")
                        .isEqualTo(HttpStatus.OK) }
                .let { convertFromJson(it.body.toString())}

        with(res) {
            assertThat(size)
                        .`as`("we have only one element")
                        .isEqualTo(1)
            with(this[0]) {
                assertThat(id)
                        .`as`("id is 1")
                        .isEqualTo(1)
                assertThat(name)
                        .`as`("name is Hello")
                        .isEqualTo("Hello")
                assertThat(slug)
                        .`as`("slug is slug")
                        .isEqualTo("slug")
                assertThat(symbol)
                        .`as`("symbol is BTC")
                        .isEqualTo("BTC")
                assertThat(url)
                        .`as`("url is null")
                        .isNull()

            }
        }
    }

    @Test
    fun `Assert that the context has been wired`() {
        assertThat(coinDataController).`as`("is wired").isNotNull
    }

    private fun populateDb() = coinDataRepo.save(CoinData(
            id = 1,
            slug = "slug",
            symbol = "BTC",
            name = "Hello",
            totalSupply = 1,
            maxSupply = 2,
            circulatingSupply = 3
    ))
}