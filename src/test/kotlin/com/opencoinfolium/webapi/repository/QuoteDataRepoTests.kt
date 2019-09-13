package com.opencoinfolium.webapi.repository

import com.opencoinfolium.webapi.entity.CoinData
import com.opencoinfolium.webapi.entity.Quote
import com.opencoinfolium.webapi.testutils.DbLoader
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.OffsetDateTime
import java.time.ZoneOffset

@RunWith(SpringRunner::class)
@SpringBootTest
class QuoteDataRepoTests {

    @Autowired
    lateinit var dbLoader: DbLoader

    @Autowired
    lateinit var quoteDataRepo: ICoinQuoteRepo

    @Autowired
    lateinit var coinDataRepo: ICoinDataRepo

    @Before
    fun contextLoads() {
        dbLoader.prepareDatabase()
    }

    @Test
    fun `Assert that the context has been wired`() {
        assertThat(dbLoader).`as`("is wired").isNotNull
        assertThat(quoteDataRepo).`as`("is wired").isNotNull
        assertThat(coinDataRepo).`as`("is wired").isNotNull
    }

    @Test
    fun `Assert that an item can be stored and read`() {
        coinDataRepo.save(CoinData(
                id = 1,
                slug = "slug",
                symbol = "BTC",
                name = "Hello",
                totalSupply = 1,
                maxSupply = 2,
                circulatingSupply = 3
        ))



        quoteDataRepo.save(1, Quote(
                coinId = 1,
                price = 123.45,
                volume24h = 123456.0,
                marketCap = 8765432.0,
                lastUpdate = OffsetDateTime.now(ZoneOffset.UTC),
                percentageChange7d = 0.1,
                percentageChange1h = 0.1,
                percentageChange24h = 0.1
        ))

        val now = OffsetDateTime.now(ZoneOffset.UTC).plusHours(1)
        quoteDataRepo.save(1, Quote(
                coinId = 1,
                price = 456.9,
                volume24h = 123456.0,
                marketCap = 8765432.0,
                lastUpdate = now,
                percentageChange7d = 0.1,
                percentageChange1h = 0.1,
                percentageChange24h = 0.1
        ))

        assertThat(quoteDataRepo
                .findByCoinId(1).coinId)
                .`as`("read it back")
                .isEqualTo(1)
        assertThat(quoteDataRepo
                .findByCoinId(1).lastUpdate)
                .`as`("read it back")
                .isEqualTo(now)

        assertThat(quoteDataRepo
                .findByCoinId(1).price)
                .`as`("read it back")
                .isCloseTo(456.9, Offset.offset(0.01))
    }

}