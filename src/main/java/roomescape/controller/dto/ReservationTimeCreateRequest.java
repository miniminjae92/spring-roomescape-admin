package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.application.dto.ReservationTimeCreateCommand;

public record ReservationTimeCreateRequest(
        String startAt
) {
    public ReservationTimeCreateCommand toCommand() {
        return new ReservationTimeCreateCommand(LocalTime.parse(this.startAt));
    }
}
