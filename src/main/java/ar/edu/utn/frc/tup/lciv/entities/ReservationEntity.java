package ar.edu.utn.frc.tup.lciv.entities;

import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumPaymentMethod;
import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumReservationStatus;
import ar.edu.utn.frc.tup.lciv.dtos.enums.EnumRoomType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_client")
    private String clientId;

    @Column(name = "id_hotel")
    private Long hotelId;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private EnumRoomType roomType;

    @Column(name = "date_ingress")
    private LocalDate dateIngress;

    @Column(name = "date_exit")
    private LocalDate dateExit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EnumReservationStatus status;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private EnumPaymentMethod paymentMethod;
}
