package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.EleccionesServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/elecciones")
public class EleccionesController {

    @Autowired
    EleccionesServiceInterface eleccionesServiceInterface;

    @GetMapping("/distritos")
    public ResponseEntity<List<DistritoDTO>> getDistritos(@RequestParam(required = false, value = "distrito_nombre") String nombre) {
        return ResponseEntity.ok(eleccionesServiceInterface.getDistritos(nombre));
    }

    @GetMapping("/distritos/{id}")
    public ResponseEntity<DistritoDTO> getDistritoById(@PathVariable Long id) {
        return ResponseEntity.ok(eleccionesServiceInterface.getDistritoById(id));
    }

    @GetMapping("/distritos/{id}/secciones")
    public ResponseEntity<List<SeccionDTO>> getSeccionesByDistrito(@PathVariable Long id) {
        return ResponseEntity.ok(eleccionesServiceInterface.getSeccionesByDistrito(id));
    }

    @GetMapping("/distritos/{distritoId}/secciones/{seccionId}")
    public ResponseEntity<SeccionDTO> getSeccionByIdAndDistritoId(@PathVariable Long distritoId,
                                                                  @PathVariable Long seccionId) {
        return ResponseEntity.ok(eleccionesServiceInterface.getSeccionByIdAndDistritoId(distritoId, seccionId));
    }

    @GetMapping("/distritos/{id}/cargos")
    public ResponseEntity<List<CargoDTO>> getCargosDistrito(@PathVariable Long id) {
        return ResponseEntity.ok(eleccionesServiceInterface.getCargoByDistrito(id));
    }

    @GetMapping("/distritos/{distritoId}/cargos/{cargoId}")
    public ResponseEntity<CargoDTO> getCargoByIdAndDistritoId(@PathVariable Long distritoId,
                                                              @PathVariable Long cargoId) {
        return ResponseEntity.ok(eleccionesServiceInterface.getCargoByIdAndDistritoId(distritoId, cargoId));
    }

    @GetMapping("/distritos/{id}/resultados")
    public ResponseEntity<ResultadoDistritoDTO> getResultadosByDistrito(@PathVariable Long id) {
        return ResponseEntity.ok(eleccionesServiceInterface.getResultadosByDistrito(id));
    }

    @GetMapping("/resultados")
    public ResponseEntity<ResultadosDTO> getResultadosGenerales() {
        return null;
    }

}
