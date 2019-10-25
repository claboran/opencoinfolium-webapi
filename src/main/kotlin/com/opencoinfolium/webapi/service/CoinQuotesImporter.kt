package com.opencoinfolium.webapi.service

import com.opencoinfolium.webapi.client.IQuoteLoader
import com.opencoinfolium.webapi.client.ImageLoader
import com.opencoinfolium.webapi.client.model.CoinItem
import com.opencoinfolium.webapi.entity.CoinData
import com.opencoinfolium.webapi.entity.Quote
import com.opencoinfolium.webapi.loggingutils.LoggingAware
import com.opencoinfolium.webapi.repository.ICoinDataRepo
import com.opencoinfolium.webapi.repository.ICoinQuoteRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.time.ZoneId

/**
 * Main component that retrieve all quotes and persist data.
 *
 * @author christian@laboranowitsch.de
 *
 */
interface ICoinQuotesImporter {
    /**
     * main importer entry.
     */
    fun readCoinsAndQuotes()
}

@Component
class CoinQuotesImporter @Autowired constructor(
        val quoteLoader: IQuoteLoader,
        val imageLoader: ImageLoader,
        val coinDataRepo: ICoinDataRepo,
        val quotesDataRepo: ICoinQuoteRepo
)
    : ICoinQuotesImporter, LoggingAware {

    @Transactional
    override fun readCoinsAndQuotes() = quoteLoader.loadQuotes().forEach { handleCoin(it) }

    private fun saveQuote(coinItem: CoinItem, coinData: CoinData) = quotesDataRepo.save(
            coinId = coinData.id,
            coinQuoteData = Quote(
                    coinId = coinItem.id,
                    price = coinItem.quotes.USD.price,
                    percentageChange24h = coinItem.quotes.USD.percentage_change_24h,
                    percentageChange1h = coinItem.quotes.USD.percentage_change_1h,
                    percentageChange7d = coinItem.quotes.USD.percentage_change_7d,
                    marketCap = coinItem.quotes.USD.market_cap,
                    volume24h = coinItem.quotes.USD.volume_24h,
                    lastUpdate = OffsetDateTime.now(ZoneId.of("UTC"))
            )
    )

    private fun createCoinData(coinItem: CoinItem): CoinData = CoinData(
            id = coinItem.id,
            totalSupply = coinItem.total_supply,
            maxSupply = coinItem.max_supply,
            circulatingSupply = coinItem.circulating_supply,
            name = coinItem.name,
            symbol = coinItem.symbol,
            slug = coinItem.website_slug,
            url = coinItem.website_slug + ".png"
    )

    private fun handleCoin(coinItem: CoinItem) {
        coinDataRepo.findById(coinItem.id)?.let {
            saveQuote(coinItem, it)
        } ?: run {
            val coin = createCoinData(coinItem)
            coinDataRepo.save(coin)
            coin.url?.let { imageLoader.getCoinImage(it) }
            saveQuote(coinItem, coin)
        }
    }

}