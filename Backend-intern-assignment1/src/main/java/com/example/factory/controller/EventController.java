package com.example.factory.controller;

import com.example.factory.dto.*;
import com.example.factory.service.EventIngestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventIngestService service;

    public EventController(EventIngestService service) {
        this.service = service;
    }

    @PostMapping("/batch")
    public BatchIngestResponse ingest(
        @RequestBody List<EventRequest> events) {
        return service.ingest(events);
    }
}
