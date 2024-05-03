package com.icm.gestioncargaapi.repositories;

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

    /* Registros */
    @Query("SELECT rc FROM RegistroCargasModel rc WHERE rc.carrilesModel.id = :carrilId")
    List<RegistroCargasModel> findByCarrilId(@Param("carrilId") Long carrilId);


    @Query("SELECT rc FROM RegistroCargasModel rc WHERE rc.carrilesModel.id = :carrilId")
    Page<RegistroCargasModel> findByCarrilId(@Param("carrilId") Long carrilId, Pageable pageable);

    @Query("SELECT rc.diaCarga AS fecha, COUNT(rc) AS cantidad FROM RegistroCargasModel rc WHERE rc.carrilesModel.id = :carrilId GROUP BY rc.diaCarga")
    List<Map<String, Object>> groupByDiaCargaAndCount(@Param("carrilId") Long carrilId);

}
