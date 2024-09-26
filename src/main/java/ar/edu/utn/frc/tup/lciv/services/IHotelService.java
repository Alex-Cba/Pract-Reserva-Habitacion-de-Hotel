package ar.edu.utn.frc.tup.lciv.services;

import ar.edu.utn.frc.tup.lciv.dtos.ReservationDTOs.ReservationDTO;
import ar.edu.utn.frc.tup.lciv.dtos.ReservationDTOs.ReservationWriteDTO;

public interface IHotelService {
    ReservationDTO createReservation(ReservationWriteDTO reservationWriteDTO);

    ReservationDTO getReservation(Long idReserva);
}
