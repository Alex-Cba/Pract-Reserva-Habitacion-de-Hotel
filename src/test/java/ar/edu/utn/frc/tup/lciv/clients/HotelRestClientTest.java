package ar.edu.utn.frc.tup.lciv.clients;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumRoomType;
import ar.edu.utn.frc.tup.lciv.records.PriceRoomRecord;
import ar.edu.utn.frc.tup.lciv.records.RoomRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class HotelRestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HotelRestClient hotelRestClient;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAvailableRoom() {
        RoomRecord roomRecord = new RoomRecord(EnumRoomType.SIMPLE, 1L, LocalDate.now(), true);
        ResponseEntity<RoomRecord> responseEntity = new ResponseEntity<>(roomRecord, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(RoomRecord.class)))
                .thenReturn(responseEntity);

        ResponseEntity<RoomRecord> result = hotelRestClient.GetAvailableRoom(1L, EnumRoomType.SIMPLE, LocalDate.now());

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(roomRecord, result.getBody());
    }

    @Test
    public void testGetRoomPrice() {
        PriceRoomRecord priceRoomRecord = new PriceRoomRecord(1L, EnumRoomType.SIMPLE, BigDecimal.valueOf(2655));
        ResponseEntity<PriceRoomRecord> responseEntity = new ResponseEntity<>(priceRoomRecord, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(PriceRoomRecord.class)))
                .thenReturn(responseEntity);

        ResponseEntity<PriceRoomRecord> result = hotelRestClient.GetRoomPrice(1L, EnumRoomType.SIMPLE);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(priceRoomRecord, result.getBody());
    }
}

