package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;
    /*Estos son dos servicios que se inyectan en el controlador*/

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids().getHead(), null), HttpStatus.OK);
    }


    @GetMapping(path = "/invert")
    public ResponseEntity<ResponseDTO> invert() {

        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200, "SE han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> ChangeExtremes() {
        listSEService.changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                404, "se ha intercambiado los extremos",
                null), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<ResponseDTO> addkid(@RequestBody KidDTO kidDTO) {
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "la ubicacion no existe", null)
                    , HttpStatus.OK);
        }
        listSEService.add(
                new Kid(kidDTO.getIdentification(),
                        kidDTO.getName(), kidDTO.getAge(),
                        kidDTO.getGender(), location));
        return new ResponseEntity<>(new ResponseDTO(
                200, "se ha adicionado el kid",
                null), HttpStatus.OK);


    }

    @GetMapping(path = "/kidsbylocations/{age}")
    public ResponseEntity<ResponseDTO> getKidsByLocation() {
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocations()) {
            int count = listSEService.getCountKidsBylocationsCode(loc.getCode());
            int male = listSEService.getCountKidsBylocationsCode(loc.getCode());
            int female = listSEService.getCountKidsBylocationsCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,female,male,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbyDepartament")
    public ResponseEntity<ResponseDTO> getKidsByDepartament() {
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocationsByCodeSize(5)) {
            int count = listSEService.getCountKidsBylocationsCode(loc.getCode());
            int male = listSEService.getCountKidsBylocationsCode(loc.getCode());
            int female = listSEService.getCountKidsBylocationsCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,female,male,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, kidsByLocationDTOList, null)
                , HttpStatus.OK);
    }

    @GetMapping(path = "/deleteKidByAge")
    public ResponseEntity<ResponseDTO> deleteKidByAge() {
        return new ResponseEntity<>(new ResponseDTO(
                200, "el nino fue eliminado",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/deletebyage/{age}")
    public ResponseEntity<ResponseDTO> DeleteByAge(@PathVariable byte age) {
        listSEService.deleteKidbyAge(age);
        return new ResponseEntity<>(new ResponseDTO(200,
                "niños eliminados ", null), HttpStatus.OK);

    }
    @PostMapping(path ="lostposition")
    public ResponseEntity<ResponseDTO>loseposition(@RequestBody Map<String, Object> requestBody){
        String id = (String) requestBody.get("id");
        Integer lose = (Integer) requestBody.get("lose");
        listSEService.LosePositionKid(id,lose);
        return new ResponseEntity<>(new ResponseDTO(200,"posicones re ordenadas",null),HttpStatus.OK);
    }

}

