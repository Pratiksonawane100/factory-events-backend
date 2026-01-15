package com.example.factory.service;

import com.example.factory.dto.*;
import com.example.factory.model.MachineEvent;
import com.example.factory.repository.MachineEventRepository;
import com.example.factory.util.ValidationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EventIngestService {

    private final MachineEventRepository repo;

    public EventIngestService(MachineEventRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public BatchIngestResponse ingest(List<EventRequest> events) {

        BatchIngestResponse res = new BatchIngestResponse();

        for (EventRequest e : events) {

            if (!ValidationUtil.isValid(e)) {
                res.rejected++;
                res.rejections.add(
                    new BatchIngestResponse.Rejection(
                        e.eventId, "INVALID"));
                continue;
            }

            Instant now = Instant.now();
            Optional<MachineEvent> old =
                    repo.findByEventId(e.eventId);

            if (old.isEmpty()) {
                repo.save(toEntity(e, now));
                res.accepted++;
                continue;
            }

            MachineEvent m = old.get();

            if (samePayload(m, e)) {
                res.deduped++;
            } else if (now.isAfter(m.getReceivedTime())) {
                update(m, e, now);
                repo.save(m);
                res.updated++;
            } else {
                res.deduped++;
            }
        }

        return res;
    }

    private boolean samePayload(
        MachineEvent m, EventRequest e) {

        return m.getEventTime().equals(e.eventTime)
            && m.getDurationMs() == e.durationMs
            && m.getDefectCount() == e.defectCount;
    }

    private MachineEvent toEntity(
        EventRequest e, Instant now) {

        MachineEvent m = new MachineEvent();
        m.setEventId(e.eventId);
        m.setEventTime(e.eventTime);
        m.setReceivedTime(now);
        m.setMachineId(e.machineId);
        m.setFactoryId(e.factoryId);
        m.setLineId(e.lineId);
        m.setDurationMs(e.durationMs);
        m.setDefectCount(e.defectCount);
        return m;
    }

    private void update(
        MachineEvent m, EventRequest e, Instant now) {

        m.setEventTime(e.eventTime);
        m.setDurationMs(e.durationMs);
        m.setDefectCount(e.defectCount);
        m.setReceivedTime(now);
    }
}
