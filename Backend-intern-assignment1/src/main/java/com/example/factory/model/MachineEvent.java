package com.example.factory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
    name = "machine_event",
    uniqueConstraints = @UniqueConstraint(columnNames = "event_id")
)
@Getter
@Setter
public class MachineEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false, unique = true)
    private String eventId;

    @Column(name = "event_time", nullable = false)
    private Instant eventTime;

    @Column(name = "received_time", nullable = false)
    private Instant receivedTime;

    @Column(name = "machine_id", nullable = false)
    private String machineId;

    @Column(name = "factory_id", nullable = false)
    private String factoryId;

    @Column(name = "line_id", nullable = false)
    private String lineId;

    @Column(name = "duration_ms")
    private long durationMs;

    @Column(name = "defect_count")
    private int defectCount;
}
