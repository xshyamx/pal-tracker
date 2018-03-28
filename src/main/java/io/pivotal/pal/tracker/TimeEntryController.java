package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
  private TimeEntryRepository repository;

  public TimeEntryController(TimeEntryRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
    return new ResponseEntity(repository.create(timeEntryToCreate), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TimeEntry> read(@PathVariable("id") long id) {
    TimeEntry found = repository.find(id);
    return (found == null)
        ? new ResponseEntity(HttpStatus.NOT_FOUND)
        : ResponseEntity.ok(found);
  }

  @GetMapping
  public ResponseEntity<List<TimeEntry>> list() {
    return ResponseEntity.ok(repository.list());
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@PathVariable("id") long id, @RequestBody TimeEntry timeEntry) {
    TimeEntry updated = repository.update(id, timeEntry);
    return updated == null
        ? new ResponseEntity(HttpStatus.NOT_FOUND)
        : ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable("id") long id) {
    repository.delete(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
