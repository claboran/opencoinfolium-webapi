package com.opencoinfolium.webapi.config

import com.opencoinfolium.webapi.testutils.DbLoader
import com.opencoinfolium.webapi.testutils.DbLoaderImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Configuration
@Profile(RunProfile.INT_TEST)
class DbLoaderConfig {

    @Autowired
    lateinit var dataSource: DataSource

    @Value("\${importer.create.script}")
    lateinit var createScript: String

    @Value("\${importer.drop.script}")
    lateinit var dropScript: String

    @Value("\${importer.run.script}")
    var runScript: Boolean = true

    @Bean
    fun dbLoader(): DbLoader = DbLoaderImpl(
            createScriptName = createScript,
            dropScriptName = dropScript,
            dataSource = dataSource,
            runScript = runScript
    )
}