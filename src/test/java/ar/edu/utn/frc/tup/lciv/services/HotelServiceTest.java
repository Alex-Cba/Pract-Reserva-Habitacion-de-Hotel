package ar.edu.utn.frc.tup.lciv.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.utn.frc.tup.lciv.clients.HotelRestClient;
import ar.edu.utn.frc.tup.lciv.dtos.ReservationDTOs.ReservationWriteDTO;
import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumPaymentMethod;
import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumRoomType;
import ar.edu.utn.frc.tup.lciv.dtos.exceptions.AppException;
import ar.edu.utn.frc.tup.lciv.entities.ReservationEntity;
import ar.edu.utn.frc.tup.lciv.records.PriceRoomRecord;
import ar.edu.utn.frc.tup.lciv.records.RoomRecord;
import ar.edu.utn.frc.tup.lciv.repositories.ReservationRepository;
import ar.edu.utn.frc.tup.lciv.services.Impl.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class HotelServiceTest {

    @Mock
    private HotelRestClient hotelRestClient;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateReservation_Successful() {
        // Arrange
        ReservationWriteDTO reservationWriteDTO = new ReservationWriteDTO();
        reservationWriteDTO.setHotelId(1L);
        reservationWriteDTO.setClientId("AA11BB22CC33");
        reservationWriteDTO.setRoomType(EnumRoomType.SIMPLE);
        reservationWriteDTO.setIngressDate(LocalDate.now().plusDays(2));
        reservationWriteDTO.setExitDate(LocalDate.now().plusDays(5));
        reservationWriteDTO.setPaymentMethod(EnumPaymentMethod.TARJETA_DEBITO);

        RoomRecord roomRecord = new RoomRecord(EnumRoomType.SIMPLE, 1L, LocalDate.of(2024, 9, 24), true);
        PriceRoomRecord priceRoomRecord = new PriceRoomRecord(1L, EnumRoomType.SIMPLE, BigDecimal.valueOf(100));



        when(hotelRestClient.GetAvailableRoom(anyLong(), eq(EnumRoomType.SIMPLE), any(LocalDate.class)))
                .thenReturn(ResponseEntity.ok(roomRecord));

        when(hotelRestClient.GetRoomPrice(anyLong(), eq(EnumRoomType.SIMPLE)))
                .thenReturn(ResponseEntity.ok(priceRoomRecord));

        ReservationEntity savedReservation = new ReservationEntity();
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(savedReservation);

        // Act
        var result = hotelService.createReservation(reservationWriteDTO);

        // Assert
        assertNotNull(result);
        verify(reservationRepository, times(1)).save(any(ReservationEntity.class));
    }

    @Test
    public void testCreateReservation_IngressDateBeforeNow_ThrowsException() {
        // Arrange
        ReservationWriteDTO reservationWriteDTO = new ReservationWriteDTO();
        reservationWriteDTO.setIngressDate(LocalDate.now().minusDays(1));
        reservationWriteDTO.setExitDate(LocalDate.now().plusDays(5));

        assertThrows(AppException.class, () -> hotelService.createReservation(reservationWriteDTO));
    }

    @Test
    public void testCreateReservation_IngressDateAfterExitDate_ThrowsException() {
        ReservationWriteDTO reservationWriteDTO = new ReservationWriteDTO();
        reservationWriteDTO.setIngressDate(LocalDate.now().plusDays(5));
        reservationWriteDTO.setExitDate(LocalDate.now().plusDays(2));

        assertThrows(AppException.class, () -> hotelService.createReservation(reservationWriteDTO));
    }
}

