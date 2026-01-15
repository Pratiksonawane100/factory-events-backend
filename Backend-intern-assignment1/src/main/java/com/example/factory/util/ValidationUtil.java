package com.example.factory.util;

import com.example.factory.dto.EventRequest;
import java.time.Instant;

public class ValidationUtil {

    public static boolean isValid(EventRequest e) {

        if (e.durationMs < 0 ||
            e.durationMs > 6 * 60 * 60 * 1000)
            return false;

        if (e.eventTime.isAfter(
            Instant.now().plusSeconds(15 * 60)))
            return false;

        return true;
    }
}
