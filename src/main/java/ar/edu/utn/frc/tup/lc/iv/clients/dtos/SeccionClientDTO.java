package ar.edu.utn.frc.tup.lc.iv.clients.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeccionClientDTO {

    private Long seccionId;
    private String seccionNombre;
    private Long distritoId;


}
