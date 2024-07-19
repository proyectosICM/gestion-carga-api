package com.icm.gestioncargaapi.controllers;

import com.icm.gestioncargaapi.components.ErrorResponseBuilder;
import com.icm.gestioncargaapi.dto.SedeCarrilesDTO;
import com.icm.gestioncargaapi.models.CarrilesModel;
import com.icm.gestioncargaapi.services.CarrilesService;
import com.icm.gestioncargaapi.validators.CarrilesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/carriles")
public class CarrilesControler {
    @Autowired
    private CarrilesService carrilesService;

    @Autowired
    private CarrilesValidator carrilesValidator;

    @Autowired
    private ErrorResponseBuilder errorResponseBuilder;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(carrilesService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CarrilesModel>> findAllPagened(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CarrilesModel> carrilesPage = carrilesService.findAllPagened(pageable);
        return new ResponseEntity<>(carrilesPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<CarrilesModel> existing = carrilesService.findById(id);
        if (existing.isPresent()) {
            return ResponseEntity.ok(existing.get());
        } else {
            return errorResponseBuilder.buildNotFoundError("el carril");
        }
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<CarrilesModel>> findByEmpresaId(@PathVariable Long sedeId) {
        List<CarrilesModel> carriles = carrilesService.findByEmpresaId(sedeId);
        return ResponseEntity.ok(carriles);
    }

    @PostMapping
    public ResponseEntity<?> saveCarril(@RequestBody CarrilesModel carril) {
        ResponseEntity<?> validationResponse = carrilesValidator.validarDatos(carril);
        if (validationResponse != null) {
            return validationResponse;
        }

        CarrilesModel savedCarril = carrilesService.saveCarril(carril);
        return new ResponseEntity<>(savedCarril, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarril(@PathVariable Long id, @RequestBody CarrilesModel carril) {

        ResponseEntity<?> validationResponse = carrilesValidator.validarDatos(carril);
        if (validationResponse != null) {
            return validationResponse;
        }

        CarrilesModel updatedCarril = carrilesService.updateCarril(id, carril);
        if (updatedCarril != null) {
            return new ResponseEntity<>(updatedCarril, HttpStatus.OK);
        } else {
            return errorResponseBuilder.buildNotFoundError("el carril");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarril(@PathVariable Long id) {
        Optional<CarrilesModel> carril = carrilesService.findById(id);
        if (!carril.isPresent()) {
            return errorResponseBuilder.buildNotFoundError("el carril");
        }

        carrilesService.deleteCarril(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
