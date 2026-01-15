package com.example.factory.service;

import com.example.factory.dto.StatsResponse;
import com.example.factory.model.MachineEvent;
import com.example.factory.repository.MachineEventRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class StatsService {

    private final MachineEventRepository repo;

    public StatsService(MachineEventRepository repo) {
        this.repo = repo;
    }

    public StatsResponse stats(
        String machineId, Instant start, Instant end) {

        List<MachineEvent> events =
            repo.findStats(machineId, start, end);

        long defects = events.stream()
            .filter(e -> e.getDefectCount() >= 0)
            .mapToLong(MachineEvent::getDefectCount)
            .sum();

        double hours =
            Duration.between(start, end)
                    .toSeconds() / 3600.0;

        double rate = hours == 0 ? 0 : defects / hours;

        StatsResponse r = new StatsResponse();
        r.machineId = machineId;
        r.start = start;
        r.end = end;
        r.eventsCount = events.size();
        r.defectsCount = defects;
        r.avgDefectRate = rate;
        r.status = rate < 2 ? "Healthy" : "Warning";

        return r;
    }
}
