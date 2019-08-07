package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    HashMap<Long, TimeEntry> map = new HashMap<Long, TimeEntry>(10);
    private long counter = 0;
    @Override
    public TimeEntry update(long id, TimeEntry any) {
    TimeEntry timeEntry = find(id);
        if(null != timeEntry) {
            timeEntry.setProjectId(any.getProjectId());
            timeEntry.setDate(any.getDate());
            timeEntry.setHours(any.getHours());
            timeEntry.setUserId(any.getUserId());
            return timeEntry;
        }
    return null;
    }

    public TimeEntry create(TimeEntry timeEntry) {

        if(0 == timeEntry.getId()) {
            ++counter;
            timeEntry.setId(counter);
        }
        map.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(Long id) {
        if(map.containsKey(id)) {
            return map.get(id);
        }
        return null;
    }

    public List<TimeEntry> list() {
        List<TimeEntry> list = new ArrayList<TimeEntry>(10);
        for(Map.Entry<Long, TimeEntry> entry: map.entrySet())
        {
            list.add(entry.getValue());
        }
        return list;
    }

    @Override
    public void delete(long timeEntryId) {
        TimeEntry timeEntry = find(timeEntryId);
        map.remove(timeEntry.getId());
    }
}
