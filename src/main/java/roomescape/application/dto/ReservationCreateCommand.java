package roomescape.application.dto;

import java.time.LocalDate;

public record ReservationCreateCommand(
        String name,
        LocalDate date,
        Long timeId
) {
}
