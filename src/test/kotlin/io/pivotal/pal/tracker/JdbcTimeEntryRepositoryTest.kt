package io.pivotal.pal.tracker

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import java.time.LocalDate
import java.util.*

class JdbcTimeEntryRepositoryTest {
    lateinit private var repo:JdbcTimeEntryRepository

    @BeforeEach
    fun setup() {
        val builder = EmbeddedDatabaseBuilder()
        builder.setType(EmbeddedDatabaseType.H2)
        builder.addScript("schema-h2.sql")
        repo = JdbcTimeEntryRepository(builder.build())
    }

    @Test
    fun create() {
        val createdTimeEntry = repo.create(TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8))
        val expected = TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8)
        Assertions.assertThat(createdTimeEntry).isEqualTo(expected)

        val readEntry = repo.find(createdTimeEntry.id)
        println(readEntry)
        Assertions.assertThat(readEntry).isEqualTo(expected)
    }

    @Test
    fun find() {
        val createdTimeEntry = repo.create(TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8))
        val expected = TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8)
        Assertions.assertThat(createdTimeEntry).isEqualTo(expected)

        val readEntry = repo.find(1L)
        Assertions.assertThat(readEntry).isEqualTo(expected)
    }

    @Test
    fun list() {
        repo.create(TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8))
        repo.create(TimeEntry(789L, 654L, LocalDate.parse("2017-01-07"), 4))
        val expected = Arrays.asList(
                TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8),
                TimeEntry(2L, 789L, 654L, LocalDate.parse("2017-01-07"), 4)
        )
        Assertions.assertThat(repo.list()).isEqualTo(expected)
    }

    @Test
    fun update() {
        val created = repo.create(TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8))
        val updatedEntry = repo.update(
                created.id,
                TimeEntry(321L, 654L, LocalDate.parse("2017-01-09"), 5))

        val expected = TimeEntry(created.id, 321L, 654L, LocalDate.parse("2017-01-09"), 5)
        Assertions.assertThat(updatedEntry).isEqualTo(expected)
        Assertions.assertThat(repo.find(created.id)).isEqualTo(expected)
    }

    @Test
    fun delete() {
        val created = repo.create(TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8))
        repo.delete(created.id)
        Assertions.assertThat(repo.list()).isEmpty()
    }

}