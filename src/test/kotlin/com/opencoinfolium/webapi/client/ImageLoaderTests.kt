package com.opencoinfolium.webapi.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

/**
 * Tests loading of coin images by means of {@link ImageLoader}.
 *
 * @author christian@laboranowitsch.de
 *
 */

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ImageLoaderTests @Autowired constructor(val imageLoader: ImageLoader) {


    @Test
    fun `Assert that the context has been wired`() {
        assertThat(imageLoader).`as`("is wired").isNotNull
    }

    @Test
    fun `Assert that we can load an image`() {
        File("./src/test/test-images").also { file -> file.mkdirs() }

        imageLoader.getCoinImage("ethereum-classic.png")

        Files.list(Paths.get("./src/test/test-images"))
                .filter { it.fileName.endsWith("ethereum-classic.png") }
                .also {  assertThat(it.toList().size).`as`("has size one").isEqualTo(1) }
                .let { File("./src/test/test-images/ethereum-classic.png").delete() }
                .let { File("./src/test/test-images").delete() }

    }
}