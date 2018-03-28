package io.pivotal.pal.tracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.util.Arrays.asList


class TimeEntryControllerTest {
    lateinit private var timeEntryRepository: TimeEntryRepository
    lateinit private var controller:TimeEntryController

    @BeforeEach
    fun setup() {
        timeEntryRepository = mock(TimeEntryRepository::class.java)
        controller = TimeEntryController(timeEntryRepository)
    }

    @Test
    fun testCreate() {
        val timeEntryToCreate = TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8)
        val expectedResult = TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8)

        doReturn(expectedResult)
                .`when`(timeEntryRepository)
                .create(any(TimeEntry::class.java))

        val response = controller.create(timeEntryToCreate)
        verify(timeEntryRepository).create(timeEntryToCreate)
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body).isEqualTo(expectedResult)
    }

    @Test
    fun testRead() {
        val expected = TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8)
        doReturn(expected)
                .`when`(timeEntryRepository)
                .find(1L)

        val response = controller.read(1L)
        verify(timeEntryRepository).find(1L)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(expected)
    }

    @Test
    fun testList() {
        val expected = asList(
                TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8),
                TimeEntry(2L, 789L, 321L, LocalDate.parse("2017-01-07"), 4)
        )
        doReturn(expected)
                .`when`(timeEntryRepository)
                .list()

        val response = controller.list()

        verify(timeEntryRepository).list()
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(expected)
    }

    @Test
    fun testUpdate() {
        val expected = TimeEntry(1L, 987L, 654L, LocalDate.parse("2017-01-07"), 4)
        doReturn(expected)
                .`when`(timeEntryRepository)
                .update(eq(1L), any(TimeEntry::class.java))

        val response = controller.update(1L, expected)

        verify(timeEntryRepository).update(1L, expected)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(expected)
    }

    @Test
    fun testUpdateNotFound() {
        doReturn(null)
                .`when`(timeEntryRepository)
                .update(eq(1L), any(TimeEntry::class.java))

        val response = controller.update(1L, TimeEntry())
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun testDelete() {
        val response = controller.delete(1L)
        verify(timeEntryRepository).delete(1L)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }
}