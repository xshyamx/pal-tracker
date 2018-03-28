package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class TimeEntryController {
    TimeEntryRepository repository;

    public TimeEntryController(TimeEntryRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<TimeEntry> create(TimeEntry timeEntryToCreate) {
        TimeEntry savedTimeEntry = repository.create(timeEntryToCreate);
        return ResponseEntity.ok(savedTimeEntry);
    }

    public ResponseEntity<TimeEntry> read(long l) {
        TimeEntry findTimeEntry = repository.find(l);
        return ResponseEntity.ok(findTimeEntry);
    }

    public ResponseEntity<List<TimeEntry>> list() {
       List<TimeEntry> listTimeEntry = repository.list();
       return ResponseEntity.ok(listTimeEntry);
    }

    public ResponseEntity update(long l, TimeEntry expected) {
        TimeEntry updateTimeEntry = repository.update(l,expected);
        return ResponseEntity.ok(updateTimeEntry);
    }

    public ResponseEntity<TimeEntry> delete(long l) {
        TimeEntry deleteTimeEntry = repository.delete(l);
        return ResponseEntity.ok(deleteTimeEntry);
    }
}
