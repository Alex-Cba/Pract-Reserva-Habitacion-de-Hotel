package ar.edu.utn.frc.tup.lciv.dtos.ReservationDTOs;

import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumPaymentMethod;
import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumRoomType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationWriteDTO {
    @JsonProperty("id_hotel")
    private Long hotelId;

    @JsonProperty("id_cliente")
    private String clientId;

    @JsonProperty("tipo_habitacion")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private EnumRoomType roomType;

    @JsonProperty("fecha_ingreso")
    private LocalDate ingressDate;

    @JsonProperty("fecha_salida")
    private LocalDate exitDate;

    @JsonProperty("medio_pago")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private EnumPaymentMethod paymentMethod;
}
