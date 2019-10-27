package com.opencoinfolium.webapi.service

import com.opencoinfolium.webapi.loggingutils.LoggingAware
import com.opencoinfolium.webapi.loggingutils.logger
import com.opencoinfolium.webapi.repository.CoinDataRepo
import com.opencoinfolium.webapi.testutils.DbLoader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

@ExtendWith(SpringExtension::class)
@SpringBootTest
class CoinQuotesImporterTests @Autowired constructor(
        val dbLoader: DbLoader,
        val coinQuotesImporter: CoinQuotesImporter,
        val coinDataRepo: CoinDataRepo
) : LoggingAware {


    @BeforeEach
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