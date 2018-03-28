package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;

import java.sql.Time;
import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private long id = 1;
    private Map<Long, TimeEntry> db = new HashMap<>();

    @Override
    public TimeEntry create(TimeEntry any) {
        any.setId(id++);
        db.put(any.getId(), any);
        return any;
    }

    @Override
    public TimeEntry find(long id) {
        return db.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(db.values());
    }

    @Override
    public TimeEntry update(long id, TimeEntry any) {
        TimeEntry updated = db.get(id);
        updated.setProjectId(any.getProjectId());
        updated.setDate(any.getDate());
        updated.setHours(any.getHours());
        updated.setUserId(any.getUserId());
        return updated;
    }

    @Override
    public TimeEntry delete(long id) {
        return db.remove(id);
    }
}
