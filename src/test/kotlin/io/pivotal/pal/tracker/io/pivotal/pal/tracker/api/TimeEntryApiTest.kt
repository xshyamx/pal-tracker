package io.pivotal.pal.tracker.io.pivotal.pal.tracker.api

import com.jayway.jsonpath.JsonPath.parse
import io.pivotal.pal.tracker.PalTrackerApplication
import io.pivotal.pal.tracker.TimeEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [PalTrackerApplication::class], webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimeEntryApiTest constructor(@Autowired private val restTemplate: TestRestTemplate) {

    private val timeEntry = TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8)

    private fun createTimeEntry(): Long {
        val entity = HttpEntity(timeEntry)
        val response = restTemplate.exchange("/time-entries", HttpMethod.POST, entity, TimeEntry::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        return response.body.id
    }

    @Test
    fun testCreate() {
        val createResponse:ResponseEntity<String> = restTemplate.postForEntity("/time-entries", timeEntry, String::class)
        val createJson = parse(createResponse.body)
        assertThat(createJson.read("$.id", Long::class.java)).isGreaterThan(0)
        assertThat(createJson.read("$.projectId", Long::class.java)).isEqualTo(123L)
        assertThat(createJson.read("$.userId", Long::class.java)).isEqualTo(456L)
        assertThat(createJson.read("$.date", String::class.java)).isEqualTo("2017-01-08")
        assertThat(createJson.read("$.hours", Long::class.java)).isEqualTo(8)
    }

    @Test
    fun testRead() {
        val id = createTimeEntry()
        val readResponse:ResponseEntity<String> = restTemplate.getForEntity("/time-entries/$id", String::class)
        assertThat(readResponse.statusCode).isEqualTo(HttpStatus.OK)
        val readJson = parse(readResponse.body)
        assertThat(readJson.read("$.id", Long::class.java)).isEqualTo(id)
        assertThat(readJson.read("$.projectId", Long::class.java)).isEqualTo(123L)
        assertThat(readJson.read("$.userId", Long::class.java)).isEqualTo(456L)
        assertThat(readJson.read("$.date", String::class.java)).isEqualTo("2017-01-08")
        assertThat(readJson.read("$.hours", Long::class.java)).isEqualTo(8)
    }

    @Test
    fun testUpdate() {
        val id = createTimeEntry()
        val updatedTimeEntry = TimeEntry(2L, 3L, LocalDate.parse("2017-01-09"), 9)
        val updateResponse = restTemplate.exchange("/time-entries/$id", HttpMethod.PUT, HttpEntity(updatedTimeEntry, null), String::class.java)
        assertThat(updateResponse.statusCode).isEqualTo(HttpStatus.OK)
        val updateJson = parse(updateResponse.body)
        assertThat(updateJson.read("$.id", Long::class.java)).isEqualTo(id)
        assertThat(updateJson.read("$.projectId", Long::class.java)).isEqualTo(2L)
        assertThat(updateJson.read("$.userId", Long::class.java)).isEqualTo(3L)
        assertThat(updateJson.read("$.date", String::class.java)).isEqualTo("2017-01-09")
        assertThat(updateJson.read("$.hours", Long::class.java)).isEqualTo(9)
    }

    @Test
    fun testDelete() {
        val id = createTimeEntry()
        val deleteResponse = restTemplate.exchange("/time-entries/$id", HttpMethod.DELETE, null, String::class.java)
        assertThat(deleteResponse.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
        val deletedReadResponse = this.restTemplate.getForEntity("/time-entries/$id", String::class.java)
        assertThat(deletedReadResponse.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}