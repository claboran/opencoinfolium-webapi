package com.opencoinfolium.webapi.repository

import com.opencoinfolium.webapi.entity.CoinData
import com.opencoinfolium.webapi.loggingutils.LoggingAware
import com.opencoinfolium.webapi.loggingutils.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import javax.sql.DataSource

interface ICoinDataRepo {
    fun findById(id: Long): CoinData?
    fun findAll(): List<CoinData>
    fun save(coinData: CoinData)
    fun update(id: Long, url: String)
}

@Component
class CoinDataRepo @Autowired constructor(
        dataSource: DataSource,
        @Value("\${importer.tbl.coinbasicdata}") tblName: String
) : ICoinDataRepo, LoggingAware {

    companion object Params {
        const val ID_PARAM = "id"
        const val NAME_PARAM = "name"
        const val SLUG_PARAM = "slug"
        const val SYMBOL_PARAM = "symbol"
        const val URL_PARAM = "image_url"
        const val CIRCULATING_SUPPLY_PARAM = "circulating_supply"
        const val TOTAL_SUPPLY_PARAM = "total_supply"
        const val MAX_SUPPLY_PARAM = "max_supply"
        val queryResultMapper = { r: ResultSet, _: Int ->
            CoinData(
                    id = r.getLong(ID_PARAM),
                    name = r.getString(NAME_PARAM),
                    symbol = r.getString(SYMBOL_PARAM),
                    slug = r.getString(SLUG_PARAM),
                    url = r.getString(URL_PARAM) ?: null,
                    circulatingSupply = r.getLong(CIRCULATING_SUPPLY_PARAM),
                    maxSupply = r.getLong(MAX_SUPPLY_PARAM),
                    totalSupply = r.getLong(TOTAL_SUPPLY_PARAM)
            )
        }
    }


    val jdbcTemplate = NamedParameterJdbcTemplate(dataSource)
    val findAllQueryStmt = "select * from $tblName"
    val findByIdQueryStmt = "select * from $tblName where $ID_PARAM = :$ID_PARAM"
    val insertStmt = "insert into $tblName  " +
            "($ID_PARAM, $NAME_PARAM, $SLUG_PARAM, $SYMBOL_PARAM," +
            " $CIRCULATING_SUPPLY_PARAM, $TOTAL_SUPPLY_PARAM, $MAX_SUPPLY_PARAM)" +
            " values(:$ID_PARAM, :$NAME_PARAM, :$SLUG_PARAM, :$SYMBOL_PARAM," +
            " :$CIRCULATING_SUPPLY_PARAM, :$TOTAL_SUPPLY_PARAM, :$MAX_SUPPLY_PARAM)"
    val updateStmt = "update $tblName set $URL_PARAM = :$URL_PARAM where $ID_PARAM = :$ID_PARAM"


    override fun findById(id: Long): CoinData? {
        val paramSource = MapSqlParameterSource()
        paramSource.addValue(ID_PARAM, id)
        val res = jdbcTemplate.query(findByIdQueryStmt, paramSource, queryResultMapper)
        return if (res.size > 0) res[0].also { logger().info(it.toString()) } else null
    }

    override fun findAll(): List<CoinData> {
        return jdbcTemplate.query(findAllQueryStmt, queryResultMapper).also { logger().info(it.toString()) }
    }

    override fun save(coinData: CoinData) {
        val paramSource = MapSqlParameterSource()
        paramSource.addValues(mapOf(
                ID_PARAM to coinData.id,
                NAME_PARAM to coinData.name,
                SYMBOL_PARAM to coinData.symbol,
                SLUG_PARAM to coinData.slug,
                CIRCULATING_SUPPLY_PARAM to coinData.circulatingSupply,
                MAX_SUPPLY_PARAM to coinData.maxSupply,
                TOTAL_SUPPLY_PARAM to coinData.maxSupply
        ))
        jdbcTemplate.update(insertStmt, paramSource)

    }

    override fun update(id: Long, url: String) {
        val paramSource = MapSqlParameterSource()
        paramSource.addValues(mapOf(
                ID_PARAM to id,
                URL_PARAM to url
        ))
        jdbcTemplate.update(updateStmt, paramSource)
    }
}