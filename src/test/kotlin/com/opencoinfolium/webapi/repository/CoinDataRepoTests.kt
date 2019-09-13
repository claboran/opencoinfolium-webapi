package com.opencoinfolium.webapi.repository

import com.opencoinfolium.webapi.entity.CoinData
import com.opencoinfolium.webapi.testutils.DbLoader
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class CoinDataRepoTests {

    @Autowired
    lateinit var dbLoader: DbLoader

    @Autowired
    lateinit var coinDataRepo: ICoinDataRepo

    @Before
    fun contextLoads() {
        dbLoader.prepareDatabase()
    }

    @Test
    fun `Assert that the context has been wired`() {
        assertThat(dbLoader).`as`("is wired").isNotNull
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
        assertThat(coinDataRepo
                .findById(1).id)
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
                .findById(2).id)
                .`as`("read back with null")
                .isEqualTo(2)

    }

    @Test(expected = EmptyResultDataAccessException::class)
    fun `Assert that an exception is thrown in case of an item not found`() {
        coinDataRepo
                .findById(1)
    }

    @Test
    fun `Assert that the update operation could be performed`() {
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
                .findById(1).url)
                .`as`("read back and check url")
                .isEqualTo("/myurl")

    }
}