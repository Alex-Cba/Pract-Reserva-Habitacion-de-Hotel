package ar.edu.utn.frc.tup.lciv.clients;

import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumRoomType;
import ar.edu.utn.frc.tup.lciv.records.PriceRoomRecord;
import ar.edu.utn.frc.tup.lciv.records.RoomRecord;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Data
@Service
public class HotelRestClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${externalApi}")
    private String domainUrl;

    public HotelRestClient() {
        if (domainUrl == null || domainUrl.isEmpty()) {
            this.domainUrl = "http://localhost:8080";
        }
    }

    public ResponseEntity<RoomRecord> GetAvailableRoom(Long hotelId, EnumRoomType roomType, LocalDate date) {
        try {
            var response = restTemplate.getForEntity(domainUrl + "/habitacion/disponibilidad?hotel_id=" + hotelId +
                                                        "&tipo_habitacion=" + roomType +"&fecha="+ date, RoomRecord.class);
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<PriceRoomRecord> GetRoomPrice(Long hotelId, EnumRoomType roomType) {
        try {
            var response = restTemplate.getForEntity(domainUrl + "/habitacion/precio?hotel_id=" + hotelId +
                    "&tipo_habitacion=" + roomType, PriceRoomRecord.class);
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
