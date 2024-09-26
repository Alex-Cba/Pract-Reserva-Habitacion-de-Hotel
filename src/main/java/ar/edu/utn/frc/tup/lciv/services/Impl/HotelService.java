package ar.edu.utn.frc.tup.lciv.services.Impl;

import ar.edu.utn.frc.tup.lciv.clients.HotelRestClient;
import ar.edu.utn.frc.tup.lciv.dtos.ReservationDTOs.ReservationDTO;
import ar.edu.utn.frc.tup.lciv.dtos.ReservationDTOs.ReservationWriteDTO;
import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumPaymentMethod;
import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumReservationStatus;
import ar.edu.utn.frc.tup.lciv.dtos.exceptions.AppException;
import ar.edu.utn.frc.tup.lciv.dtos.exceptions.NotFoundException;
import ar.edu.utn.frc.tup.lciv.entities.ReservationEntity;
import ar.edu.utn.frc.tup.lciv.records.RoomRecord;
import ar.edu.utn.frc.tup.lciv.repositories.ReservationRepository;
import ar.edu.utn.frc.tup.lciv.services.IHotelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

@Service
@AllArgsConstructor
public class HotelService implements IHotelService {

    @Autowired
    private final HotelRestClient hotelRestClient;

    @Autowired
    private final ReservationRepository reservationRepository;

    @Override
    public ReservationDTO createReservation(ReservationWriteDTO reservationWriteDTO) {
        if (reservationWriteDTO.getIngressDate().isBefore(LocalDate.now())
            || reservationWriteDTO.getExitDate().isBefore(LocalDate.now())) {
            throw new AppException("La fecha de ingreso o egreso es menor a la actual");
        }

        if (reservationWriteDTO.getIngressDate().isAfter(reservationWriteDTO.getExitDate())) {
            throw new AppException("La fecha de ingreso no puede ser despues de la fecha de egreso");
        }

        LocalDate currentDate = reservationWriteDTO.getIngressDate();
        LocalDate exitDate = reservationWriteDTO.getExitDate();

        RoomRecord roomRecord = null;
        //Recorrer entorno de fecha
        while (!currentDate.isAfter(exitDate)) {
            roomRecord = hotelRestClient.GetAvailableRoom(reservationWriteDTO.getHotelId(),
                    reservationWriteDTO.getRoomType(),
                    currentDate).getBody();

            if (roomRecord != null && !roomRecord.isAvailable()) {
                break;
            }

            currentDate = currentDate.plusDays(1);
        }

        var priceOfRoom = hotelRestClient.GetRoomPrice(roomRecord.hotelId(), roomRecord.roomType()).getBody();

        BigDecimal calculatedPrice = null;
        if (priceOfRoom != null && priceOfRoom.price().compareTo(BigDecimal.ZERO) > 0) {
            calculatedPrice = CalculatorPriceRoomByPaymentMethod(priceOfRoom.price(), reservationWriteDTO.getPaymentMethod());
            calculatedPrice = CalculatorPriceRoomByDate(calculatedPrice, reservationWriteDTO.getIngressDate());
        }
        
        var reservationToDb = new ReservationEntity();
        reservationToDb.setHotelId(roomRecord.hotelId());
        reservationToDb.setClientId(reservationWriteDTO.getClientId());
        reservationToDb.setRoomType(roomRecord.roomType());
        reservationToDb.setDateIngress(reservationWriteDTO.getIngressDate());
        reservationToDb.setDateExit(reservationWriteDTO.getExitDate());
        reservationToDb.setPaymentMethod(reservationWriteDTO.getPaymentMethod());
        reservationToDb.setPrice(calculatedPrice != null && calculatedPrice.compareTo(BigDecimal.ZERO) > 0 ? priceOfRoom.price() : new BigDecimal(0) );
        reservationToDb.setStatus(roomRecord.isAvailable()
                                    && priceOfRoom != null
                                    && calculatedPrice.compareTo(BigDecimal.ZERO) > 0
                                ? EnumReservationStatus.EXITOSA
                                : EnumReservationStatus.PENDIENTE);
        reservationRepository.save(reservationToDb);

        var response = new ReservationDTO(reservationToDb);
        return response;
    }

    private BigDecimal CalculatorPriceRoomByDate(BigDecimal price, LocalDate ingressDate) {
        Month month = ingressDate.getMonth();

        if (month == Month.JANUARY || month == Month.FEBRUARY || month == Month.JULY || month == Month.AUGUST) {
            var priceAdd = price.multiply(new BigDecimal(0.3));
            price = price.add(priceAdd);

        } else if (month == Month.MARCH || month == Month.APRIL || month == Month.OCTOBER || month == Month.NOVEMBER) {
            var discount = price.multiply(new BigDecimal(0.1));
            price = price.subtract(discount);

        }

        return price;
    }

    private BigDecimal CalculatorPriceRoomByPaymentMethod(BigDecimal price, EnumPaymentMethod paymentMethod) {

        switch (paymentMethod) {
            case EFECTIVO -> {
                var discount = price.multiply(new BigDecimal(0.25));
                price = price.subtract(discount);
            }
            case TARJETA_DEBITO -> {
                var discount = price.multiply(new BigDecimal(0.1));
                price = price.subtract(discount);
            }
        }

        return price;
    }

    @Override
    public ReservationDTO getReservation(Long idReserva) {
        var checkReservation = reservationRepository.findById(idReserva).orElseThrow(() -> new NotFoundException("El reserva no existe"));

        var response = new ReservationDTO(checkReservation);
        return response;
    }
}
