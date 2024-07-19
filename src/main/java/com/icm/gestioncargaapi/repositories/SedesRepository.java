package com.icm.gestioncargaapi.repositories;

import com.icm.gestioncargaapi.models.RegistroCargasModel;
import com.icm.gestioncargaapi.models.SedesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SedesRepository extends JpaRepository<SedesModel, Long> {
    List<SedesModel> findByEmpresasModelId(Long id);
}
