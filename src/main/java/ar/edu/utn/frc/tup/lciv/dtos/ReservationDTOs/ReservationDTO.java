package ar.edu.utn.frc.tup.lciv.dtos.ReservationDTOs;

import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumPaymentMethod;
import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumReservationStatus;
import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumRoomType;
import ar.edu.utn.frc.tup.lciv.entities.ReservationEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    @JsonProperty("id_reserva")
    private Long id;

    @JsonProperty("id_cliente")
    private String clientId;

    @JsonProperty("id_hotel")
    private Long hotelId;

    @JsonProperty("tipo_habitacion")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private EnumRoomType roomType;

    @JsonProperty("fecha_ingreso")
    private LocalDate dateIngress;

    @JsonProperty("fecha_salida")
    private LocalDate dateExit;

    @JsonProperty("estado_reserva")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private EnumReservationStatus status;

    @JsonProperty("precio")
    private BigDecimal price;

    @JsonProperty("medio_pago")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private EnumPaymentMethod paymentMethod;


    public ReservationDTO(ReservationEntity reservationDb) {
        this.id = reservationDb.getId();
        this.clientId = reservationDb.getClientId();
        this.hotelId = reservationDb.getHotelId();
        this.roomType = reservationDb.getRoomType();
        this.dateIngress = reservationDb.getDateIngress();
        this.dateExit = reservationDb.getDateExit();
        this.status = reservationDb.getStatus();
        this.price = reservationDb.getPrice();
        this.paymentMethod = reservationDb.getPaymentMethod();
    }
}
