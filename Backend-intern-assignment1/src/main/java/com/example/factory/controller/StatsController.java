package com.example.factory.controller;

import com.example.factory.dto.*;
import com.example.factory.repository.MachineEventRepository;
import com.example.factory.service.StatsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;
    private final MachineEventRepository repo;

    public StatsController(
        StatsService statsService,
        MachineEventRepository repo) {

        this.statsService = statsService;
        this.repo = repo;
    }

    @GetMapping
    public StatsResponse stats(
        @RequestParam String machineId,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Instant start,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Instant end) {

        return statsService.stats(machineId, start, end);
    }

    @GetMapping("/top-defect-lines")
    public List<TopDefectLineResponse> top(
        @RequestParam String factoryId,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Instant from,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Instant to,
        @RequestParam int limit) {

        return repo.topDefectLines(factoryId, from, to)
            .stream()
            .limit(limit)
            .map(TopDefectLineResponse::from)
            .toList();
    }
}
