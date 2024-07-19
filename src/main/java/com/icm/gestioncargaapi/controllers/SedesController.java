package com.icm.gestioncargaapi.controllers;

import com.icm.gestioncargaapi.models.SedesModel;
import com.icm.gestioncargaapi.services.SedesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sedes")
public class SedesController {

    @Autowired
    private SedesService sedesService;

    @GetMapping("/xempresa/{empresaId}")
    public ResponseEntity<List<SedesModel>> getSedesByEmpresaId(@PathVariable Long empresaId) {
        List<SedesModel> sedes = sedesService.getSedesByEmpresaId(empresaId);
        return ResponseEntity.ok(sedes);
    }

    @GetMapping
    public List<SedesModel> getAllSedes() {
        return sedesService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SedesModel> getSedeById(@PathVariable Long id) {
        Optional<SedesModel> sede = sedesService.getById(id);
        if (sede.isPresent()) {
            return ResponseEntity.ok(sede.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SedesModel> createSede(@RequestBody SedesModel sedesModel) {
        SedesModel newSede = sedesService.save(sedesModel);
        return ResponseEntity.ok(newSede);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SedesModel> updateSede(@PathVariable Long id, @RequestBody SedesModel sedesModel) {
        try {
            SedesModel updatedSede = sedesService.update(id, sedesModel);
            return ResponseEntity.ok(updatedSede);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSede(@PathVariable Long id) {
        Optional<SedesModel> existingSede = sedesService.getById(id);
        if (existingSede.isPresent()) {
            sedesService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
