package com.opencoinfolium.webapi.config

import org.h2.tools.Server
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.sql.SQLException

@Configuration
@Profile(value = [RunProfile.LOCAL_DEV_H2])
class H2LocalDevConfig {

    @Bean
    @ConditionalOnExpression("\${h2.tcp.enabled:false}") //default is disabled
    @Throws(SQLException::class)
    fun h2TcpServer(): Server {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start()
    }

}