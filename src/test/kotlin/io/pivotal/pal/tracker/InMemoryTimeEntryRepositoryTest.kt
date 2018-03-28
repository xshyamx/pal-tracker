package io.pivotal.pal.tracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.Arrays.asList


class InMemoryTimeEntryRepositoryTest {
    lateinit private var repo:InMemoryTimeEntryRepository

    @BeforeEach
    fun setup() {
        repo = InMemoryTimeEntryRepository()
    }

    @Test
    fun create() {
        val createdTimeEntry = repo.create(TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8))
        val expected = TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8)
        assertThat(createdTimeEntry).isEqualTo(expected)

        val readEntry = repo.find(createdTimeEntry.id)
        assertThat(readEntry).isEqualTo(expected)
    }

    @Test
    fun find() {
        val createdTimeEntry = repo.create(TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8))
        val expected = TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8)
        assertThat(createdTimeEntry).isEqualTo(expected)

        val readEntry = repo.find(1L)
        assertThat(readEntry).isEqualTo(expected)
    }

    @Test
    fun list() {
        repo.create(TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8))
        repo.create(TimeEntry(789L, 654L, LocalDate.parse("2017-01-07"), 4))
        val expected = asList(
                TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8),
                TimeEntry(2L, 789L, 654L, LocalDate.parse("2017-01-07"), 4)
        )
        assertThat(repo.list()).isEqualTo(expected)
    }

    @Test
    fun update() {
        val created = repo.create(TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8))
        val updatedEntry = repo.update(
                created.id,
                TimeEntry(321L, 654L, LocalDate.parse("2017-01-09"), 5))

        val expected = TimeEntry(created.id, 321L, 654L, LocalDate.parse("2017-01-09"), 5)
        assertThat(updatedEntry).isEqualTo(expected)
        assertThat(repo.find(created.id)).isEqualTo(expected)
    }

    @Test
    fun delete() {
        val created = repo.create(TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8))
        repo.delete(created.id)
        assertThat(repo.list()).isEmpty()
    }

}