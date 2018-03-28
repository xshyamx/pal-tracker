package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    TimeEntryRepository repository;

    public TimeEntryController(TimeEntryRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TimeEntry> create(TimeEntry timeEntryToCreate) {
        TimeEntry savedTimeEntry = repository.create(timeEntryToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTimeEntry);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long l) {
        TimeEntry findTimeEntry = repository.find(l);
        if ( findTimeEntry == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(findTimeEntry);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TimeEntry>> list() {
       List<TimeEntry> listTimeEntry = repository.list();
       return ResponseEntity.ok(listTimeEntry);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(long l, TimeEntry expected) {
        TimeEntry updateTimeEntry = repository.update(l,expected);
        if (updateTimeEntry == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(updateTimeEntry);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity delete(long l) {
        repository.delete(l);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
