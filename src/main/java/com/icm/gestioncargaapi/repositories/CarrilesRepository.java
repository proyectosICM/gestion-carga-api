package com.icm.gestioncargaapi.repositories;

import com.icm.gestioncargaapi.models.CarrilesModel;
import com.icm.gestioncargaapi.models.SedesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrilesRepository extends JpaRepository<CarrilesModel, Long> {
    List<CarrilesModel> findBySedesModelId(Long id);
    @Query("SELECT c.sedesModel.id, COUNT(c) FROM CarrilesModel c GROUP BY c.sedesModel.id")
    List<Object[]> countCarrilesBySede();
}
