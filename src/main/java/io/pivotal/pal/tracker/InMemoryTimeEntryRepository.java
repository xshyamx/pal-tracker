package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
  private long id = 1;
  private Map<Long, TimeEntry> db = new HashMap<>();

  @Override
  public TimeEntry create(TimeEntry timeEntry) {
    timeEntry.setId(id++);
    db.put(timeEntry.getId(), timeEntry);
    return timeEntry;
  }

  @Override
  public TimeEntry update(long id, TimeEntry timeEntry) {
    TimeEntry found = find(id);
    if ( found != null ) {
      found.copyFrom(timeEntry);
      return found;
    }
    return null;
  }

  @Override
  public TimeEntry delete(long id) {
    return db.remove(id);
  }

  @Override
  public TimeEntry find(long id) {
    return db.get(id);
  }

  @Override
  public List<TimeEntry> list() {
    return new ArrayList<>(db.values());
  }
}
