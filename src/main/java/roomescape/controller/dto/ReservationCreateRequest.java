package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.application.dto.ReservationCreateCommand;

public record ReservationCreateRequest(
        String date,
        String name,
        Long timeId
) {
    public ReservationCreateCommand toCommand() {
        return new ReservationCreateCommand(
                this.name,
                LocalDate.parse(this.date),
                this.timeId
        );
    }
}
