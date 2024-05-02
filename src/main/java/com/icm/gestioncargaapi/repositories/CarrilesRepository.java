package com.icm.gestioncargaapi.repositories;

import com.icm.gestioncargaapi.models.CarrilesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrilesRepository extends JpaRepository<CarrilesModel, Long> {
}
