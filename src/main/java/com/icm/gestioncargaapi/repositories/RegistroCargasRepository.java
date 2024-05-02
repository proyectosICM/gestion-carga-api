package com.icm.gestioncargaapi.repositories;

import com.icm.gestioncargaapi.dto.RegistroCargasDiaDTO;
import com.icm.gestioncargaapi.models.RegistroCargasModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface RegistroCargasRepository extends JpaRepository<RegistroCargasModel, Long> {
    Page<RegistroCargasModel> findByDiaCarga(LocalDate diaCarga, Pageable pageable);
    long countByDiaCarga(LocalDate diaCarga);
    @Query("SELECT rc FROM RegistroCargasModel rc WHERE rc.carrilesModel.id = :carrilId")
    List<RegistroCargasModel> findByCarrilId(@Param("carrilId") Long carrilId);

    @Query("SELECT rc FROM RegistroCargasModel rc WHERE rc.diaCarga = :diaCarga AND rc.carrilesModel.id = :carrilId")
    List<RegistroCargasModel> findByDiaCargaAndCarrilId(@Param("diaCarga") LocalDate diaCarga, @Param("carrilId") Long carrilId);
}
