package com.opencoinfolium.webapi.repository

import com.opencoinfolium.webapi.entity.Quote
import com.opencoinfolium.webapi.loggingutils.LoggingAware
import com.opencoinfolium.webapi.loggingutils.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.ZoneOffset
import javax.sql.DataSource

interface ICoinQuoteRepo {
    fun save(coinId: Long, coinQuoteData: Quote)
    fun findByCoinId(coinId: Long): Quote
}

@Component
class CoinQuoteRepo @Autowired constructor(
        dataSource: DataSource,
        @Value("\${importer.tbl.coinquote}") coinQuoteTblName: String,
        @Value("\${importer.tbl.coinquoteview}") coinQuoteLastDataTblName: String
) : ICoinQuoteRepo, LoggingAware {

    companion object Params {
        const val ID_PARAM = "id"
        const val COIN_DATA_PARAM = "coin_id"
        const val QUOTE_PARAM = "quote"
        const val VOLUME_24H_PARAM = "volume_24h"
        const val MARKET_CAP_PARAM = "market_cap"
        const val QUOTED_AT_PARAM = "quoted_at"
        const val PERCENTAGE_CHANGE_1H_PARAM = "percent_change_1h"
        const val PERCENTAGE_CHANGE_24H_PARAM = "percent_change_24h"
        const val PERCENTAGE_CHANGE_7D_PARAM = "percent_change_7d"
    }

    val jdbcTemplate = NamedParameterJdbcTemplate(dataSource)
    val insertStmt = "insert into $coinQuoteTblName  " +
            "($COIN_DATA_PARAM, $QUOTE_PARAM, $VOLUME_24H_PARAM," +
            " $MARKET_CAP_PARAM, $QUOTED_AT_PARAM, $PERCENTAGE_CHANGE_1H_PARAM," +
            " $PERCENTAGE_CHANGE_24H_PARAM, $PERCENTAGE_CHANGE_7D_PARAM)" +
            " values(:$COIN_DATA_PARAM, :$QUOTE_PARAM, :$VOLUME_24H_PARAM," +
            " :$MARKET_CAP_PARAM, :$QUOTED_AT_PARAM, :$PERCENTAGE_CHANGE_1H_PARAM, :$PERCENTAGE_CHANGE_24H_PARAM," +
            " :$PERCENTAGE_CHANGE_7D_PARAM)"

    val lastQuoteQueryStmt = "select * from $coinQuoteLastDataTblName where $COIN_DATA_PARAM = :$COIN_DATA_PARAM"

    override fun save(coinId: Long, coinQuoteData: Quote) {
        val paramSource = MapSqlParameterSource()
        paramSource.addValues(mapOf(
                COIN_DATA_PARAM to coinId,
                QUOTE_PARAM to coinQuoteData.price,
                VOLUME_24H_PARAM to coinQuoteData.volume24h,
                MARKET_CAP_PARAM to coinQuoteData.marketCap,
                QUOTED_AT_PARAM to Timestamp.from(coinQuoteData.lastUpdate.toInstant()),
                PERCENTAGE_CHANGE_1H_PARAM to coinQuoteData.percentageChange1h,
                PERCENTAGE_CHANGE_24H_PARAM to coinQuoteData.percentageChange24h,
                PERCENTAGE_CHANGE_7D_PARAM to coinQuoteData.percentageChange7d
        ))
        jdbcTemplate.update(insertStmt, paramSource)
    }

    override fun findByCoinId(coinId: Long): Quote {

        val paramSource = MapSqlParameterSource()
        paramSource.addValues(mapOf(
                COIN_DATA_PARAM to coinId
        ))

        return jdbcTemplate.queryForObject(lastQuoteQueryStmt, paramSource) {
            r: ResultSet, _: Int ->
            Quote(
                    coinId = r.getLong(COIN_DATA_PARAM),
                    price = r.getDouble(QUOTE_PARAM),
                    lastUpdate = r.getTimestamp(QUOTED_AT_PARAM).toInstant().atOffset(ZoneOffset.UTC),
                    marketCap = r.getDouble(MARKET_CAP_PARAM),
                    volume24h = r.getDouble(VOLUME_24H_PARAM),
                    percentageChange1h = r.getDouble(PERCENTAGE_CHANGE_1H_PARAM),
                    percentageChange24h = r.getDouble(PERCENTAGE_CHANGE_24H_PARAM),
                    percentageChange7d = r.getDouble(PERCENTAGE_CHANGE_7D_PARAM)
            )
        }!! .also { logger().info(it.toString()) }
    }

}