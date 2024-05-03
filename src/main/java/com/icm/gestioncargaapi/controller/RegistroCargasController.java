package com.icm.gestioncargaapi.controller;

import com.icm.gestioncargaapi.components.ErrorResponseBuilder;
import com.icm.gestioncargaapi.models.RegistroCargasModel;
import com.icm.gestioncargaapi.services.RegistroCargasService;
import com.icm.gestioncargaapi.validators.RegistroCargasValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/registro-cargas")
public class RegistroCargasController {

    @Autowired
    private RegistroCargasService registroCargasService;

    @Autowired
    private RegistroCargasValidator registroCargasValidator;
    @Autowired
    private ErrorResponseBuilder errorResponseBuilder;

    @GetMapping("/count-day")
    public ResponseEntity<List<Map<String, Object>>> groupByDiaCargaAndCount(@RequestParam Long carrilId) {
        List<Map<String, Object>> result = registroCargasService.groupByDiaCargaAndCount(carrilId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/registros/{carrilId}")
    public List<RegistroCargasModel> findByCarrilId(@PathVariable Long carrilId) {
        return registroCargasService.findByCarrilId(carrilId);
    }

    @GetMapping
    public ResponseEntity<?> getAllRegistrosCargas() {
        return new ResponseEntity<>(registroCargasService.listarRegistros(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<RegistroCargasModel>> getAllRegistrosPaginated(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RegistroCargasModel> registrosPage = registroCargasService.listarRegistrosPaginado(pageable);
        return new ResponseEntity<>(registrosPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegistroCargaById(@PathVariable Long id) {
        Optional<RegistroCargasModel> existing = registroCargasService.listarRegistroId(id);
        if (existing.isPresent()) {
            return ResponseEntity.ok(existing.get());
        } else {
            return errorResponseBuilder.buildNotFoundError("el registro de carga");
        }
    }

    @PostMapping()
    public ResponseEntity<?> createRegistroCarga(@RequestBody RegistroCargasModel registroCargas) {
        ResponseEntity<?> validationResponse = registroCargasValidator.validarDatos(registroCargas);
        if (validationResponse != null) {
            return validationResponse;
        }

        RegistroCargasModel savedRegistroCarga = registroCargasService.crearRegistroCarga(registroCargas);
        return new ResponseEntity<>(savedRegistroCarga, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegistroCarga(@PathVariable Long id, @RequestBody RegistroCargasModel registroCargas) {
        ResponseEntity<?> validationResponse = registroCargasValidator.validarDatos(registroCargas);
        if (validationResponse != null) {
            return validationResponse;
        }

        RegistroCargasModel updatedRegistroCarga = registroCargasService.editarRegistroCarga(id, registroCargas);
        if (updatedRegistroCarga != null) {
            return new ResponseEntity<>(updatedRegistroCarga, HttpStatus.OK);
        } else {
            return errorResponseBuilder.buildNotFoundError("el registro de carga");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistroCarga(@PathVariable Long id) {
        Optional<RegistroCargasModel> data = registroCargasService.listarRegistroId(id);
        if (!data.isPresent()) {
            return errorResponseBuilder.buildNotFoundError("el registro de carga");
        }

        registroCargasService.eliminarRegistroCarga(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
