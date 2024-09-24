package ar.edu.utn.frc.tup.lc.iv.clients.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoClientDTO {

    private Long id;
    private Long distritoId;
    private Long seccionId;
    private Long cargoId;
    private Long agrupacionId;
    private String votosTipo;
    private BigDecimal votosCantidad;


}
