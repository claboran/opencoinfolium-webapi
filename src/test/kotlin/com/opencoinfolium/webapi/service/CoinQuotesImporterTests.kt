package com.opencoinfolium.webapi.service

import com.opencoinfolium.webapi.loggingutils.LoggingAware
import com.opencoinfolium.webapi.loggingutils.logger
import com.opencoinfolium.webapi.repository.CoinDataRepo
import com.opencoinfolium.webapi.repository.CoinQuoteRepo
import com.opencoinfolium.webapi.testutils.DbLoader
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

@RunWith(SpringRunner::class)
@SpringBootTest
class CoinQuotesImporterTests : LoggingAware {

    @Autowired
    lateinit var dbLoader: DbLoader

    @Autowired
    lateinit var coinQuotesImporter: CoinQuotesImporter

    @Autowired
    lateinit var coinDataRepo: CoinDataRepo

    @Before
    fun contextLoads() {
        dbLoader.prepareDatabase()
    }

    @Test
    fun `Assert that the context has been wired`() {
        assertThat(dbLoader).`as`("is wired").isNotNull
        assertThat(coinQuotesImporter).`as`("is wired").isNotNull
        assertThat(coinDataRepo).`as`("is wired").isNotNull
    }

    @Test
    fun `Assert we have successfully read initial set of data`() {
        coinQuotesImporter.readCoinsAndQuotes()
        assertThat(coinDataRepo.findById(52)?.name)
                .`as`("is Ripple")
                .isEqualTo("Ripple")

        Files.list(Paths.get("./src/test/test-images"))
                .filter { it.fileName.endsWith("ethereum-classic.png") }
                .also {  assertThat(it.toList().size).`as`("has size one").isEqualTo(1) }
        Files.list(Paths.get("./src/test/test-images"))
                .forEach {
                    logger().info("delete: $it")
                    File(it.toString()).delete()
                }
        File("./src/test/test-images").delete()
    }
}