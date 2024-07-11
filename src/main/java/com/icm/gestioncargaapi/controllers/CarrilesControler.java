package com.icm.gestioncargaapi.controllers;

import com.icm.gestioncargaapi.components.ErrorResponseBuilder;
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
    public ResponseEntity<?> getAllCarriles() {
        return new ResponseEntity<>(carrilesService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<CarrilesModel>> findBySedeId(@PathVariable Long sedeId) {
        List<CarrilesModel> carriles = carrilesService.findBySedeId(sedeId);
        return ResponseEntity.ok(carriles);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CarrilesModel>> getAllCarrilesPaginated(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CarrilesModel> carrilesPage = carrilesService.listarTodosPaginado(pageable);
        return new ResponseEntity<>(carrilesPage, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCarrilById(@PathVariable Long id) {
        Optional<CarrilesModel> existing = carrilesService.listarPorId(id);
        if (existing.isPresent()) {
            return ResponseEntity.ok(existing.get());
        } else {
            return errorResponseBuilder.buildNotFoundError("el carril");
        }
    }

    @PostMapping
    public ResponseEntity<?> createCarril(@RequestBody CarrilesModel carril) {
        ResponseEntity<?> validationResponse = carrilesValidator.validarDatos(carril);
        if (validationResponse != null) {
            return validationResponse;
        }

        CarrilesModel savedCarril = carrilesService.guardarCarril(carril);
        return new ResponseEntity<>(savedCarril, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarril(@PathVariable Long id, @RequestBody CarrilesModel carril) {

        ResponseEntity<?> validationResponse = carrilesValidator.validarDatos(carril);
        if (validationResponse != null) {
            return validationResponse;
        }

        CarrilesModel updatedCarril = carrilesService.actualizarCarril(id, carril);
        if (updatedCarril != null) {
            return new ResponseEntity<>(updatedCarril, HttpStatus.OK);
        } else {
            return errorResponseBuilder.buildNotFoundError("el carril");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarril(@PathVariable Long id) {
        Optional<CarrilesModel> carril = carrilesService.listarPorId(id);
        if (!carril.isPresent()) {
            return errorResponseBuilder.buildNotFoundError("el carril");
        }

        carrilesService.eliminarCarril(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
