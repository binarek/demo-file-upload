package demo.upload.domain.common;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class DateTimeProvider {

    private final ZoneId ZONE_ID = ZoneId.of("Europe/Warsaw");

    public LocalDate getNowLocalDate() {
        return LocalDate.now(ZONE_ID);
    }
}
