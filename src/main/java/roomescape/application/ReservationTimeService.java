package roomescape.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.dto.ReservationTimeCreateCommand;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
@Transactional(readOnly = true)
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    @Transactional
    public ReservationTime create(ReservationTimeCreateCommand command) {
        if (reservationTimeRepository.existsByStartAt(command.startAt())) {
            throw new IllegalArgumentException("이미 존재하는 예약 시간입니다.");
        }
        Long generatedId = reservationTimeRepository.save(command.startAt());
        return ReservationTime.from(generatedId, command.startAt());
    }

    @Transactional
    public void delete(Long id) {
        int affectedRows = reservationTimeRepository.deleteById(id);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("이미 삭제되었거나 존재하지 않는 예약 시간입니다.");
        }
    }
}
