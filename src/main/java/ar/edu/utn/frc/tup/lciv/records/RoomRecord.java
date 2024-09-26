package ar.edu.utn.frc.tup.lciv.records;

import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumRoomType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record RoomRecord(
        @JsonProperty("tipo_habitacion") EnumRoomType roomType,
        @JsonProperty("hotel_id") Long hotelId,
        @JsonProperty("fecha") LocalDate date,
        @JsonProperty("disponible") boolean isAvailable
) {
}
