package ar.edu.utn.frc.tup.lciv.records;

import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumRoomType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record PriceRoomRecord(
        @JsonProperty("id_hotel") Long hotelId,
        @JsonProperty("tipo_habitacion") EnumRoomType roomType,
        @JsonProperty("precio_lista") BigDecimal price
) {
}
