package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.clients.dtos.DistritoClientDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.CargoDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.DistritoDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.ResultadoDistritoDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.SeccionDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface EleccionesServiceInterface {

    public List<DistritoDTO> getDistritos(String nombre);

    public DistritoDTO getDistritoById(Long id);

    public List<CargoDTO> getCargoByDistrito(Long id);

    public CargoDTO getCargoByIdAndDistritoId(Long distritoId, Long cargoId);

    public List<SeccionDTO> getSeccionesByDistrito(Long id);

    public SeccionDTO getSeccionByIdAndDistritoId(Long distritoId, Long seccionId);

    public ResultadoDistritoDTO getResultadosByDistrito(Long id);


}
