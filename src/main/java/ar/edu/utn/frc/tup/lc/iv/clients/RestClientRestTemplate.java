package ar.edu.utn.frc.tup.lc.iv.clients;

import ar.edu.utn.frc.tup.lc.iv.clients.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClientRestTemplate {

    RestTemplate restTemplate = new RestTemplate();

    //    @Value("${api.url}")
    private String URL = "http://localhost:8080/";

    public ResponseEntity<DistritoClientDTO[]> getDistritos() {

        ResponseEntity<DistritoClientDTO[]> response = null;

        try {

            response = restTemplate.getForEntity(URL + "distritos", DistritoClientDTO[].class);

            if (response.getStatusCode().equals(200)) {

                return response;
            }

        } catch (Exception ex) {

            System.out.println("ERROR" + ex.getMessage());
        }


        return response;

    }

    public ResponseEntity<CargoClientDTO[]> getCargos() {

        ResponseEntity<CargoClientDTO[]> response = null;

        try {

            response = restTemplate.getForEntity(URL + "cargos", CargoClientDTO[].class);

            if (response.getStatusCode().equals(200)) {

                return response;
            }

        } catch (Exception ex) {

            System.out.println("ERROR" + ex.getMessage());
        }


        return response;

    }

    public ResponseEntity<SeccionClientDTO[]> getSecciones() {

        ResponseEntity<SeccionClientDTO[]> response = null;

        try {

            response = restTemplate.getForEntity(URL + "secciones", SeccionClientDTO[].class);

            if (response.getStatusCode().equals(200)) {

                return response;
            }

        } catch (Exception ex) {

            System.out.println("ERROR" + ex.getMessage());
        }


        return response;

    }


    public ResponseEntity<ResultadoClientDTO[]> getResultadosByDistrito(Long distritoId) {

        ResponseEntity<ResultadoClientDTO[]> response = null;

        try {

            response = restTemplate.getForEntity(URL + "/resultados?districtId=" + distritoId, ResultadoClientDTO[].class);

            if (response.getStatusCode().equals(200)) {

                return response;
            }

        } catch (Exception ex) {

            System.out.println("ERROR" + ex.getMessage());
        }


        return response;

    }

    public ResponseEntity<PartidoPoliticoClientDTO> getPartidoPolitico(Long id) {

        ResponseEntity<PartidoPoliticoClientDTO> response = null;

//        PartidoPoliticoClientDTO[] partidoPoliticoClientDTO = new PartidoPoliticoClientDTO[]{
//
//                new PartidoPoliticoClientDTO(1L, "LA LIBERTAD AVANZA"),
//
//                new PartidoPoliticoClientDTO(2L, "HACEMOS POR NUESTRO PAIS"),
//
//                new PartidoPoliticoClientDTO(3L, "JUNTOS POR EL CAMBIO"),
//
//                new PartidoPoliticoClientDTO(4L, "UNION POR LA PATRIA"),
//
//                new PartidoPoliticoClientDTO(5L, "FRENTE DE IZQUIERDA Y DE TRABAJADORES - UNIDAD")
//
//        };

        try {

            response = restTemplate.getForEntity(URL + "agrupaciones/" + id, PartidoPoliticoClientDTO.class);


//            return partidoPoliticoClientDTO[id.intValue()];


            if (response.getStatusCode().equals(200)) {

                return response;
            }

        } catch (Exception ex) {

            System.out.println("ERROR" + ex.getMessage());
        }


        return response;

    }


}
