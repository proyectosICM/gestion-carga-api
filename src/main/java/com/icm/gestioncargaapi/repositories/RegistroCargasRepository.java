package com.icm.gestioncargaapi.repositories;

import com.icm.gestioncargaapi.models.RegistroCargasModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface RegistroCargasRepository extends JpaRepository<RegistroCargasModel, Long> {
    Page<RegistroCargasModel> findByDiaCarga(LocalDate diaCarga, Pageable pageable);

    /**
     * Estadisticas y conteos
     */

    /* Conteo de cargas en un dia de todos los carriles  */
    @Query("SELECT rc.carrilesModel.id AS carrilId," +
            "rc.carrilesModel.nombre AS carrilNombre, " +
            " rc.diaCarga AS fecha," +
            " COUNT(rc) AS cantidad " +
            "FROM RegistroCargasModel rc " +
            "WHERE rc.diaCarga = :fecha " +
            "GROUP BY rc.carrilesModel.id, rc.carrilesModel.nombre, rc.diaCarga " +
            "ORDER BY rc.carrilesModel.id ASC")
    List<Map<String, Object>> groupByCarrilAndDiaCargaAndCount(@Param("fecha") LocalDate fecha);

    /* Conteo de cargas en un dia de un carril */
    @Query("SELECT rc.diaCarga AS fecha, COUNT(rc) AS cantidad FROM RegistroCargasModel rc WHERE rc.carrilesModel.id = :carrilId AND rc.diaCarga = :fecha GROUP BY rc.diaCarga")
    Map<String, Object> groupByDiaCargaAndCount(@Param("carrilId") Long carrilId, @Param("fecha") LocalDate fecha);


    /**
     *  Registros Listados y Paginado
     */
    @Query("SELECT rc FROM RegistroCargasModel rc WHERE rc.carrilesModel.id = :carrilId ORDER BY rc.id DESC")
    List<RegistroCargasModel> findByCarrilId(@Param("carrilId") Long carrilId);

/*
    @Query("SELECT rc FROM RegistroCargasModel rc WHERE rc.carrilesModel.id = :carrilId ORDER BY rc.id DESC")
    Page<RegistroCargasModel> findByCarrilIdOrderByDesc(@Param("carrilId") Long carrilId, Pageable pageable);
*/
    @Query("SELECT rc FROM RegistroCargasModel rc WHERE rc.carrilesModel.id = :carrilId ORDER BY rc.diaCarga DESC, rc.horaInicio DESC")
    Page<RegistroCargasModel> findByCarrilIdOrderByDesc(@Param("carrilId") Long carrilId, Pageable pageable);

    //Ultimos 7 dias - 1
    @Query("SELECT rc.diaCarga AS fecha, " +
            "       COUNT(rc) AS cantidad " +
            "FROM RegistroCargasModel rc " +
            "WHERE rc.carrilesModel.id = :carrilId " +
            "      AND rc.diaCarga >= :fechaInicio " +
            "      AND rc.diaCarga <= :fechaFin " +
            "GROUP BY rc.diaCarga")
    List<Map<String, Object>> countByDiaCarga(@Param("carrilId") Long carrilId,
                                              @Param("fechaInicio") LocalDate fechaInicio,
                                              @Param("fechaFin") LocalDate fechaFin);

    //Ultimos 7 dias - Todos
    @Query("SELECT rc.carrilesModel.id AS carrilId, " +
            "rc.carrilesModel.nombre AS carrilNombre, " +
            "rc.diaCarga AS fecha, " +
            "COUNT(rc) AS cantidad " +
            "FROM RegistroCargasModel rc " +
            "WHERE rc.diaCarga >= :fechaInicio AND rc.diaCarga <= :fechaFin " +
            "GROUP BY rc.carrilesModel.id, rc.diaCarga")
    List<Map<String, Object>> countByCarrilAndDiaCarga(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
}

