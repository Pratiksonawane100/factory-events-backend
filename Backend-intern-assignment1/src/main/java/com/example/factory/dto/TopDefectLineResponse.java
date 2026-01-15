package com.example.factory.dto;

public class TopDefectLineResponse {

    public String lineId;
    public long totalDefects;
    public long eventCount;
    public double defectsPercent;

    public static TopDefectLineResponse from(Object[] row) {
        TopDefectLineResponse r = new TopDefectLineResponse();
        r.lineId = (String) row[0];
        r.totalDefects = (long) row[1];
        r.eventCount = (long) row[2];
        r.defectsPercent =
            r.eventCount == 0 ? 0 :
            (r.totalDefects * 100.0) / r.eventCount;
        return r;
    }
}
