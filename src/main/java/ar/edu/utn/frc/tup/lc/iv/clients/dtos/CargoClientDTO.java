package ar.edu.utn.frc.tup.lc.iv.clients.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoClientDTO {


    private Long cargoId;
    private String cargoNombre;
    private Long distritoId;

}
