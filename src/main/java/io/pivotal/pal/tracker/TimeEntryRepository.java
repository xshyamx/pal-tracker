package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry create(TimeEntry any);

    public TimeEntry find(long id);

    public List<TimeEntry> list();

    public TimeEntry update(long eq, TimeEntry any);

    TimeEntry delete(long l);
}
