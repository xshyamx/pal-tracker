package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TimeEntryController {
    TimeEntryRepository repository;

    public TimeEntryController(TimeEntryRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<TimeEntry> create(TimeEntry timeEntryToCreate) {
        TimeEntry savedTimeEntry = repository.create(timeEntryToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTimeEntry);
    }

    public ResponseEntity<TimeEntry> read(long l) {
        TimeEntry findTimeEntry = repository.find(l);
        if ( findTimeEntry == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(findTimeEntry);
        }
    }

    public ResponseEntity<List<TimeEntry>> list() {
       List<TimeEntry> listTimeEntry = repository.list();
       return ResponseEntity.ok(listTimeEntry);
    }

    public ResponseEntity update(long l, TimeEntry expected) {
        TimeEntry updateTimeEntry = repository.update(l,expected);
        if (updateTimeEntry == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(updateTimeEntry);
        }
    }

    public ResponseEntity delete(long l) {
        repository.delete(l);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
