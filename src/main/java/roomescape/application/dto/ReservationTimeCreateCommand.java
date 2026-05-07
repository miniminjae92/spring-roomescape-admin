package roomescape.application.dto;

import java.time.LocalTime;

public record ReservationTimeCreateCommand(
        LocalTime startAt
) {
}
