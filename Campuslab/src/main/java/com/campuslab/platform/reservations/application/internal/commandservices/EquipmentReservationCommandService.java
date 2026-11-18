package com.campuslab.platform.reservations.application.internal.commandservices;

import com.campuslab.platform.shared.application.results.Failure;
import com.campuslab.platform.shared.application.results.Result;
import com.campuslab.platform.shared.application.results.Success;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentReservationCommandService {

    private final com.campuslab.platform.reservations.application.internal.commandservices.EquipmentReservationRepository reservationRepository;


    @Transactional
    public Result<EquipmentReservation> handle(CreateEquipmentReservationCommand command) {
        try {
            ReservationCode code = new ReservationCode(command.reservationCode());

            if (reservationRepository.existsByReservationCode(code.code())) {
                return new Failure<>(Failure.ErrorType.CONFLICT, "error.reservation.code.duplicate", code.code());
            }

            LaboratoryId laboratoryId = new LaboratoryId(command.laboratoryId());
            ReservationPeriod period = new ReservationPeriod(
                    command.reservationPeriodStartDate(),
                    command.reservationPeriodEndDate()
            );
            EquipmentBatch batch = new EquipmentBatch(
                    command.equipmentBatchQuantity(),
                    command.equipmentBatchUnit()
            );

            ReservationStatus status = resolveStatus(command.status(), command.statusProvided());

            EquipmentReservation aggregate = new EquipmentReservation(
                    code,
                    laboratoryId,
                    period,
                    batch,
                    command.accessLevel(),
                    status,
                    command.reservationNotes()
            );

            EquipmentReservation saved = reservationRepository.save(aggregate);
            return new Success<>(saved);

        } catch (IllegalArgumentException ex) {
            return new Failure<>(Failure.ErrorType.VALIDATION_ERROR, ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            return new Failure<>(Failure.ErrorType.CONFLICT, "error.reservation.code.duplicate", command.reservationCode());
        } catch (Exception ex) {
            return new Failure<>(Failure.ErrorType.INTERNAL_ERROR, "error.unexpected");
        }
    }

    private ReservationStatus resolveStatus(String rawStatus, boolean statusProvided) {
        if (!statusProvided) {
            return ReservationStatus.PENDING;
        }
        if (rawStatus == null) {
            throw new IllegalArgumentException("error.status.invalid");
        }
        try {
            return ReservationStatus.valueOf(rawStatus.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("error.status.invalid");
        }
    }
}
