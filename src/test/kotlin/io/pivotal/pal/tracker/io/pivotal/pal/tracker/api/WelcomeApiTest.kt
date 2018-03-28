package io.pivotal.pal.tracker.io.pivotal.pal.tracker.api

import io.pivotal.pal.tracker.PalTrackerApplication
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [PalTrackerApplication::class], webEnvironment= RANDOM_PORT)
class WelcomeApiTest constructor(@Autowired private val restTemplate: TestRestTemplate){

    @Test
    fun sayHello() {
        val body = restTemplate.getForObject("/", String::class.java)
        assertEquals("Hello from test", body)
    }
}