package roomescape.application;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.dto.ReservationCreateCommand;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.dto.ReservationJoinRow;

@Service
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final Clock clock;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository,
            Clock clock
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.clock = clock;
    }

    public List<Reservation> findAllReservations() {
        List<ReservationJoinRow> rows = reservationRepository.findAll();
        return rows.stream()
                .map(this::toDomain)
                .toList();
    }

    @Transactional
    public Reservation createReservation(ReservationCreateCommand command) {
        ReservationTime time = reservationTimeRepository.findById(command.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));

        LocalDate currentDate = LocalDate.now(clock);

        Reservation reservation = Reservation.createNew(
                command.name(),
                command.date(),
                time,
                currentDate
        );

        Long generatedId = reservationRepository.save(reservation);
        return Reservation.from(generatedId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Transactional
    public void deleteReservation(Long id) {
        int affectedRows = reservationRepository.deleteById(id);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("이미 삭제되었거나 존재하지 않는 예약입니다.");
        }
    }

    private Reservation toDomain(ReservationJoinRow row) {
        ReservationTime time = ReservationTime.from(row.timeId(), row.startAt());
        return Reservation.from(
                row.reservationId(),
                row.name(),
                row.date(),
                time
        );
    }
}
