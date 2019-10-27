package com.opencoinfolium.webapi.repository

import com.opencoinfolium.webapi.entity.CoinData
import com.opencoinfolium.webapi.testutils.DbLoader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * Tests loading of quotes and proper deserialization of {@link QuotesItem}.
 *
 * @author christian@laboranowitsch.de
 *
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
class CoinDataRepoTests @Autowired constructor(
        val dbLoader: DbLoader,
        val coinDataRepo: ICoinDataRepo
) {


    @BeforeEach
    fun contextLoads() {
        dbLoader.prepareDatabase()
    }

    @Test
    fun `Assert that the context has been wired`() {
        assertThat(dbLoader).`as`("is wired").isNotNull
        assertThat(coinDataRepo).`as`("is wired").isNotNull
    }

    @Test
    fun `Assert that an item can be stored and read back`() {
        coinDataRepo.save(CoinData(
                id = 1,
                slug = "slug",
                symbol = "BTC",
                name = "Hello",
                totalSupply = 1,
                maxSupply = 2,
                circulatingSupply = 3
        ))
        assertThat(coinDataRepo
                .findById(1)!!.id)
                .`as`("read it back")
                .isEqualTo(1)

        coinDataRepo.save(CoinData(
                id = 2,
                slug = "slug",
                symbol = "BTC",
                name = "Hello",
                totalSupply = 1,
                maxSupply = null,
                circulatingSupply = 3
        ))
        assertThat(coinDataRepo
                .findById(2)!!.id)
                .`as`("read back with null")
                .isEqualTo(2)

    }

    @Test
    fun `Assert that result is null when item is not present`() {
        assertThat(coinDataRepo
                .findById(1))
                .`as`("is null").isNull()
    }

    @Test
    fun `Assert that the we can update the url`() {
        coinDataRepo.save(CoinData(
                id = 1,
                slug = "slug",
                symbol = "BTC",
                name = "Hello",
                totalSupply = 1,
                maxSupply = 2,
                circulatingSupply = 3
        ))
        coinDataRepo.update(id = 1, url = "/myurl")

        assertThat(coinDataRepo
                .findById(1)!!.url)
                .`as`("read back and check url")
                .isEqualTo("/myurl")

    }
}