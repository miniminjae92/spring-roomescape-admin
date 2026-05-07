package roomescape.repository.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationJoinRow(
        Long reservationId,
        String name,
        LocalDate date,
        Long timeId,
        LocalTime startAt
) {
}
