package com.opencoinfolium.webapi.client

import com.opencoinfolium.webapi.loggingutils.LoggingAware
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.net.URL

/**
 * Image Loader component - reading and writing of coin images.
 *
 * @author christian@laboranowitsch.de
 */
@Component
class ImageLoader @Autowired constructor(
        @Value("\${importer.client.image.basepath}") val imageBaseBath: String,
        @Value("\${importer.client.image.destination}") val destination: String
) : LoggingAware {

    /**
     * Retrieves a coin based on  [image] name.
     */
    fun getCoinImage(image: String) = FileUtils
            .copyURLToFile(URL("$imageBaseBath$image"), File("$destination$image"))

}