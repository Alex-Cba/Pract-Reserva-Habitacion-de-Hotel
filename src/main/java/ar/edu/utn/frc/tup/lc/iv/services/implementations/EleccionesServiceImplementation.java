package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.clients.RestClientRestTemplate;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.ResultadoClientDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.EleccionesServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class EleccionesServiceImplementation implements EleccionesServiceInterface {


    @Autowired
    RestClientRestTemplate restClientRestTemplate;

    @Autowired
    ModelMapper modelMapper;


    public List<DistritoDTO> getDistritos(String nombre) {

        return Arrays.stream(restClientRestTemplate.getDistritos().getBody()).map(x -> modelMapper.map(x, DistritoDTO.class)).toList();
    }

    public DistritoDTO getDistritoById(Long id) {

        return getDistritos(null).stream().filter(x -> x.getId() == id).toList().get(0);

    }

    public List<CargoDTO> getCargoByDistrito(Long id) {

        return Arrays.stream(restClientRestTemplate.getCargos().getBody()).filter(x -> x.getDistritoId() == id)
                .map(x -> new CargoDTO(x.getCargoId(), x.getCargoNombre()))
                .toList();

    }

    public CargoDTO getCargoByIdAndDistritoId(Long distritoId, Long cargoId) {

        return Arrays.stream(restClientRestTemplate.getCargos().getBody())
                .filter(x -> x.getDistritoId() == distritoId && x.getCargoId() == cargoId)
                .map(x -> new CargoDTO(x.getCargoId(), x.getCargoNombre())).toList().get(0);

    }

    public List<SeccionDTO> getSeccionesByDistrito(Long id) {


        return Arrays.stream(restClientRestTemplate.getSecciones().getBody())
                .filter(x -> x.getDistritoId() == id)
                .map(x -> new SeccionDTO(x.getSeccionId(), x.getSeccionNombre())).toList();

    }

    public SeccionDTO getSeccionByIdAndDistritoId(Long distritoId, Long seccionId) {


        return Arrays.stream(restClientRestTemplate.getSecciones().getBody())
                .filter(x -> x.getDistritoId() == distritoId && x.getSeccionId() == seccionId)
                .map(x -> new SeccionDTO(x.getSeccionId(), x.getSeccionNombre())).toList().get(0);

    }

    public ResultadoDistritoDTO getResultadosByDistrito(Long id) {


        ResultadoDistritoDTO resultadoDistritoDTO = new ResultadoDistritoDTO();

        Map<String, BigDecimal> partidosPoliticosMap = new HashMap<>();


        List<ResultadoClientDTO> resultadoClientDTOList = Arrays.stream(restClientRestTemplate.getResultadosByDistrito(id).getBody()).toList();

        resultadoDistritoDTO.setId(getDistritoById(id).getId());
        resultadoDistritoDTO.setNombre(getDistritoById(id).getNombre());
        resultadoDistritoDTO.setSecciones(getSeccionesByDistrito(id).stream().map(x -> x.getNombre()).toList());

        Map<Long, String> nombresPartidosPoliticosMap = getNombresPartidosPoliticosFiltered(resultadoClientDTOList);

        resultadoClientDTOList.forEach(x -> partidosPoliticosMap.putIfAbsent(nombresPartidosPoliticosMap.get(x.getAgrupacionId()), new BigDecimal(0)));

        resultadoClientDTOList.forEach(x -> partidosPoliticosMap.putIfAbsent(x.getVotosTipo(), new BigDecimal(0)));

        partidosPoliticosMap.remove(null);

        BigDecimal total = getTotalVotosByDistrito(resultadoClientDTOList, partidosPoliticosMap, nombresPartidosPoliticosMap);

        resultadoDistritoDTO.setVotosEscrutados(total.intValue());

        resultadoDistritoDTO.setResultadosAgrupaciones(setMapOnList(partidosPoliticosMap, total));

        resultadoDistritoDTO.setResultadosAgrupaciones(orderList(resultadoDistritoDTO.getResultadosAgrupaciones()));

        resultadoDistritoDTO.setAgrupacionGanadora(resultadoDistritoDTO.getResultadosAgrupaciones().get(0).getNombre());

        return resultadoDistritoDTO;

    }

    public BigDecimal getTotalVotosByDistrito(List<ResultadoClientDTO> resultadoClientDTOList, Map<String, BigDecimal> partidosPoliticosMap, Map<Long, String> nombresPartidosPoliticosMap) {

        BigDecimal total = new BigDecimal(0);

        for (int i = 0; i < resultadoClientDTOList.size(); i++) {

            if (partidosPoliticosMap.containsKey(nombresPartidosPoliticosMap.get(resultadoClientDTOList.get(i).getAgrupacionId()))) {

                BigDecimal value = partidosPoliticosMap.get(nombresPartidosPoliticosMap.get(resultadoClientDTOList.get(i).getAgrupacionId()));

                value = value.add(resultadoClientDTOList.get(i).getVotosCantidad());

                partidosPoliticosMap.replace(nombresPartidosPoliticosMap.get(resultadoClientDTOList.get(i).getAgrupacionId()), value);


            } else if (partidosPoliticosMap.containsKey(resultadoClientDTOList.get(i).getVotosTipo())) {


                BigDecimal value = partidosPoliticosMap.get(resultadoClientDTOList.get(i).getVotosTipo());

                value = value.add(resultadoClientDTOList.get(i).getVotosCantidad());

                partidosPoliticosMap.replace(resultadoClientDTOList.get(i).getVotosTipo(), value);

            }

            total = total.add(resultadoClientDTOList.get(i).getVotosCantidad());
        }

        return total;

    }


    public List<ResultadoAgrupacionDTO> setMapOnList(Map<String, BigDecimal> partidosPoliticosMap, BigDecimal total) {


        List<ResultadoAgrupacionDTO> resultadoAgrupacionDTOList = new ArrayList<>();


        List<String> nombresPartidosPoliticos = partidosPoliticosMap.keySet().stream().toList();

        for (int i = 0; i < nombresPartidosPoliticos.size(); i++) {

            resultadoAgrupacionDTOList.add(new ResultadoAgrupacionDTO(nombresPartidosPoliticos.get(i),
                    i,
                    partidosPoliticosMap.get(nombresPartidosPoliticos.get(i)).intValue(),
                   String.format(Locale.US, "%.2f ", partidosPoliticosMap.get(nombresPartidosPoliticos.get(i)).divide(total, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)))));

        }


        return resultadoAgrupacionDTOList;

    }

    public Map<Long, String> getNombresPartidosPoliticosFiltered(List<ResultadoClientDTO> resultadoClientDTOList) {

        Map<Long, String> nombresPartidosPoliticosMap = new HashMap<>();

        resultadoClientDTOList.stream()
                .filter(x -> x.getAgrupacionId() >= 132)
                .forEach(x -> nombresPartidosPoliticosMap.putIfAbsent(x.getAgrupacionId(), restClientRestTemplate.getPartidoPolitico(x.getAgrupacionId()).getBody().getAgrupacionNombre()));


        return nombresPartidosPoliticosMap;

    }

    public List<ResultadoAgrupacionDTO> orderList(List<ResultadoAgrupacionDTO> resultadoAgrupacionDTOList) {


        resultadoAgrupacionDTOList.sort(Comparator.comparing(ResultadoAgrupacionDTO::getVotos).reversed());

        for (int i = 0; i < resultadoAgrupacionDTOList.size(); i++) {

            resultadoAgrupacionDTOList.get(i).setPosicion((i + 1));

        }

        return resultadoAgrupacionDTOList;

    }


}
