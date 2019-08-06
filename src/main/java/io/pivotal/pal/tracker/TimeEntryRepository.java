package io.pivotal.pal.tracker;

import io.pivotal.pal.trackerapi.TimeEntry;

import java.util.HashMap;
import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry update(long eq, TimeEntry any);

    public TimeEntry create(TimeEntry any);

    public TimeEntry find(Long id);

    public List<TimeEntry> list();

    public void delete(long timeEntryId);
}
