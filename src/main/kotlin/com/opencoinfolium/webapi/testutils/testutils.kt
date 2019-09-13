package com.opencoinfolium.webapi.testutils

import com.opencoinfolium.webapi.loggingutils.LoggingAware
import com.opencoinfolium.webapi.loggingutils.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.EncodedResource
import org.springframework.jdbc.datasource.DataSourceUtils
import org.springframework.jdbc.datasource.init.ScriptUtils.*
import javax.sql.DataSource

interface DbLoader {
    fun prepareDatabase()
}

class DbLoaderImpl (
        val createScriptName: String,
        val dropScriptName: String,
        val dataSource: DataSource,
        val runScript: Boolean
) : DbLoader, LoggingAware {

    override fun prepareDatabase() {
        if (runScript) {
            executeDropScript()
            executeCreateScript()
        }
    }

    private fun executeDropScript() {

        val resource = ClassPathResource(dropScriptName)
        val con = DataSourceUtils.getConnection(dataSource)

        logger().info("Executing DROP-Script: {}", dropScriptName)

        executeSqlScript(con,
                EncodedResource(resource, "UTF-8"),
                true,
                true,
                DEFAULT_COMMENT_PREFIX,
                DEFAULT_STATEMENT_SEPARATOR,
                DEFAULT_BLOCK_COMMENT_START_DELIMITER,
                DEFAULT_BLOCK_COMMENT_END_DELIMITER)

        DataSourceUtils.releaseConnection(con, dataSource)
    }

    private fun executeCreateScript() {
        val resource = ClassPathResource(createScriptName)
        val con = DataSourceUtils.getConnection(dataSource)

        logger().info("Executing DROP-Script: {}", createScriptName)

        executeSqlScript(con, EncodedResource(resource, "UTF-8"))
        DataSourceUtils.releaseConnection(con, dataSource)
    }
}