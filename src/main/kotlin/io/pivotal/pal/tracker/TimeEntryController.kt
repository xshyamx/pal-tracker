package io.pivotal.pal.tracker

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/time-entries")
class TimeEntryController(private val repository: TimeEntryRepository){

    @PostMapping
    fun create(@RequestBody expected: TimeEntry): ResponseEntity<TimeEntry> {
        val entry = repository.create(expected)
        return ResponseEntity(entry, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long): ResponseEntity<TimeEntry?> {
        val found = repository.find(id)
        return when (found != null) {
            true -> ResponseEntity(found, HttpStatus.OK)
            false -> ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping
    fun list(): ResponseEntity<List<TimeEntry>> {
        return ResponseEntity(repository.list(), HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id:Long, @RequestBody expected:TimeEntry): ResponseEntity<TimeEntry?> {
        val updated= repository.update(id, expected)
        return when(updated != null) {
            true -> ResponseEntity(updated, HttpStatus.OK)
            false -> ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id:Long): ResponseEntity<Unit> {
        val deleted = repository.delete(id)
        return when(deleted != null) {
            true -> ResponseEntity(HttpStatus.OK)
            false -> ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}