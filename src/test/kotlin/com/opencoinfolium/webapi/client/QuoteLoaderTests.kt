package com.opencoinfolium.webapi.client

import com.opencoinfolium.webapi.client.model.CurrencyItem
import com.opencoinfolium.webapi.client.model.QuoteItem
import com.opencoinfolium.webapi.client.model.QuoteItems
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Tests loading of quotes and proper deserialization of {@link QuotesItem}.
 *
 * @author christian@laboranowitsch.de
 *
 */

@RunWith(SpringRunner::class)
@SpringBootTest
class QuoteLoaderTests {

    companion object {
        const val testJsonData = "{\n" +
                "\t\"data\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 1,\n" +
                "\t\t\t\"name\": \"Bitcoin\",\n" +
                "\t\t\t\"symbol\": \"BTC\",\n" +
                "\t\t\t\"website_slug\": \"bitcoin\",\n" +
                "\t\t\t\"rank\": 1,\n" +
                "\t\t\t\"circulating_supply\": 17935487,\n" +
                "\t\t\t\"total_supply\": 17418787,\n" +
                "\t\t\t\"max_supply\": 21000000,\n" +
                "\t\t\t\"quotes\": {\n" +
                "\t\t\t\t\"USD\": {\n" +
                "\t\t\t\t\t\"price\": 10335.6005143974,\n" +
                "\t\t\t\t\t\"volume_24h\": 1446382154.76898,\n" +
                "\t\t\t\t\t\"market_cap\": 185339585355.705,\n" +
                "\t\t\t\t\t\"percentage_change_1h\": 0.64,\n" +
                "\t\t\t\t\t\"percentage_change_24h\": 0.06,\n" +
                "\t\t\t\t\t\"percentage_change_7d\": -0.75\n" +
                "\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t\"last_updated\": 1568469901\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 1027,\n" +
                "\t\t\t\"name\": \"Ethereum\",\n" +
                "\t\t\t\"symbol\": \"ETH\",\n" +
                "\t\t\t\"website_slug\": \"ethereum\",\n" +
                "\t\t\t\"rank\": 2,\n" +
                "\t\t\t\"circulating_supply\": 107738565.4365,\n" +
                "\t\t\t\"total_supply\": 103748085,\n" +
                "\t\t\t\"max_supply\": 0,\n" +
                "\t\t\t\"quotes\": {\n" +
                "\t\t\t\t\"USD\": {\n" +
                "\t\t\t\t\t\"price\": 182.578512804074,\n" +
                "\t\t\t\t\t\"volume_24h\": 383264834.356382,\n" +
                "\t\t\t\t\t\"market_cap\": 19787067624.1356,\n" +
                "\t\t\t\t\t\"percentage_change_1h\": 2.21,\n" +
                "\t\t\t\t\t\"percentage_change_24h\": 1.9,\n" +
                "\t\t\t\t\t\"percentage_change_7d\": 5.98\n" +
                "\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t\"last_updated\": 1568469902\n" +
                "\t\t}\n" +
                "\t]," +
                "\"metadata\": {" +
                "\"timestamp\": 1569826504," +
                "\"num_cryptocurrencies\": 629," +
                "\"error\": null" +
                "}" +
                "}"

        val testCoin = QuoteItems(
                id= 1,
                name = "Bitcoin",
                symbol = "BTC",
                website_slug = "bitcoin",
                rank = 1,
                circulating_supply = 17935487,
                total_supply = 17418787,
                max_supply = 21000000,
                last_updated = 1568469901,
                quotes = CurrencyItem(
                        USD = QuoteItem(
                                price = 10335.6005143974,
                                volume_24h = 1446382154.76898,
                                market_cap = 185339585355.705,
                                percentage_change_1h = 0.64,
                                percentage_change_24h = 0.06,
                                percentage_change_7d = -0.75
                        )
                )
        )
    }
    @Autowired
    lateinit var quoteLoader: QuoteLoader;

    @Test
    fun `Assert that the context has been wired`() {
        assertThat(quoteLoader).`as`("is wired").isNotNull
    }


    @Test
    fun `Assert that we receive quote data and we have a quote for BitCoin`() {
        val quotes = quoteLoader.loadQuotes()
        quotes.filter { it.id == 1L }
        assertThat(quotes.filter { it.id == 1L }[0].name)
                .`as`("has an coin item with id = 1 and name = Bitcoin")
                .isEqualTo("Bitcoin")

    }

    @Test
    fun `Assert that we can parse ticker test data properly`() {
        assertThat(convertFromJson(testJsonData).data[0])
                .`as`("is equal to testCoin data")
                .isEqualTo(testCoin)
    }
}