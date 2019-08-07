package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository,
                               MeterRegistry meterRegistry) {
        this.timeEntryRepository=timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntryNew=timeEntryRepository.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(timeEntryNew,HttpStatus.CREATED);
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry timeEntryNew = timeEntryRepository.find(timeEntryId);
        if(null == timeEntryNew)
        {
        actionCounter.increment();
        return new ResponseEntity(timeEntryNew,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(timeEntryNew,HttpStatus.OK);
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry timeEntryNew = timeEntryRepository.update(timeEntryId, expected);
        if(null == timeEntryNew)
        {
            actionCounter.increment();
            return new ResponseEntity(timeEntryNew,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(timeEntryNew,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        List<TimeEntry> timeEntryList=timeEntryRepository.list();
        return new ResponseEntity(timeEntryList, HttpStatus.OK);
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
