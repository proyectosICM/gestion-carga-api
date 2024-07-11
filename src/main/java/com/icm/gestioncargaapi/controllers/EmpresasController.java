package com.icm.gestioncargaapi.controllers;

import com.icm.gestioncargaapi.models.EmpresasModel;
import com.icm.gestioncargaapi.services.EmpresasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
public class EmpresasController {

    @Autowired
    private EmpresasServices empresasServices;

    @GetMapping
    public List<EmpresasModel> getAllEmpresas() {
        return empresasServices.getAll();
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<EmpresasModel> findByUsername(@PathVariable String username) {
        Optional<EmpresasModel> empresa = empresasServices.findByUsername(username);
        return empresa.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresasModel> getEmpresaById(@PathVariable Long id) {
        Optional<EmpresasModel> empresa = empresasServices.getById(id);
        if (empresa.isPresent()) {
            return ResponseEntity.ok(empresa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EmpresasModel> createEmpresa(@RequestBody EmpresasModel empresa) {
        EmpresasModel nuevaEmpresa = empresasServices.save(empresa);
        return ResponseEntity.ok(nuevaEmpresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresasModel> updateEmpresa(@PathVariable Long id, @RequestBody EmpresasModel empresa) {
        try {
            EmpresasModel empresaActualizada = empresasServices.update(id, empresa);
            return ResponseEntity.ok(empresaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        empresasServices.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
