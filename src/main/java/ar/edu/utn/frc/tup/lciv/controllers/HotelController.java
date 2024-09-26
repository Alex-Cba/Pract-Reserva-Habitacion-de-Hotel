package ar.edu.utn.frc.tup.lciv.controllers;

import ar.edu.utn.frc.tup.lciv.dtos.ReservationDTOs.ReservationDTO;
import ar.edu.utn.frc.tup.lciv.dtos.ReservationDTOs.ReservationWriteDTO;
import ar.edu.utn.frc.tup.lciv.services.IHotelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hotels")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @PostMapping("/reserva")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationWriteDTO reservationWriteDTO) {
        ReservationDTO response = hotelService.createReservation(reservationWriteDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reserva/{id_reserva}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id_reserva) {
        ReservationDTO response = hotelService.getReservation(id_reserva);
        return ResponseEntity.ok(response);
    }
}
