package com.example.factory.dto;

import java.time.Instant;

public class EventRequest {
    public String eventId;
    public Instant eventTime;
    public String machineId;
    public String factoryId;
    public String lineId;
    public long durationMs;
    public int defectCount;
}
