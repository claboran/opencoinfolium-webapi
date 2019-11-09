package com.opencoinfolium.webapi.service

import com.opencoinfolium.webapi.config.RunProfile
import com.opencoinfolium.webapi.loggingutils.LoggingAware
import com.opencoinfolium.webapi.loggingutils.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

interface ICoinScheduler {
    fun scheduleImport()
}

@Component
@Profile(value = [RunProfile.LOCAL_DEV_H2, RunProfile.LOCAL_DEV, RunProfile.PROD])
class CoinScheduler @Autowired constructor(
        val coinQuotesImporter: ICoinQuotesImporter
): ICoinScheduler, LoggingAware {

    @Scheduled(cron = "\${importer.import.scheduler}")
    override fun scheduleImport() =
        logger().info("Scheduled Import...").run { coinQuotesImporter.readCoinsAndQuotes() }

}