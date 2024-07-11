package com.icm.gestioncargaapi.repositories;

import com.icm.gestioncargaapi.models.EmpresasModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresasRepository extends JpaRepository<EmpresasModel, Long> {
    Optional<EmpresasModel> findByUsername(String username);
}
