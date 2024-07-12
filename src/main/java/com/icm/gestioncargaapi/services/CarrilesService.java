package com.icm.gestioncargaapi.services;

import com.icm.gestioncargaapi.dto.SedeCarrilesDTO;
import com.icm.gestioncargaapi.models.CarrilesModel;
import com.icm.gestioncargaapi.repositories.CarrilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarrilesService {

    @Autowired
    CarrilesRepository carrilesRepository;

    public List<SedeCarrilesDTO> contarCarrilesPorSede() {
        List<Object[]> results = carrilesRepository.countCarrilesBySede();
        return results.stream()
                .map(result -> new SedeCarrilesDTO((Long) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

    public List<CarrilesModel> listarTodos(){
        return carrilesRepository.findAll();
    }

    public List<CarrilesModel> findBySedeId(Long sedeId) {
        return carrilesRepository.findBySedesModelId(sedeId);
    }

    public Page<CarrilesModel> listarTodosPaginado(Pageable pageable){
        return carrilesRepository.findAll(pageable);
    }

    public Optional<CarrilesModel> listarPorId(Long id){
        return carrilesRepository.findById(id);
    }

    public CarrilesModel guardarCarril(CarrilesModel carrilesModel){
        return carrilesRepository.save(carrilesModel);
    }

    public CarrilesModel actualizarCarril(Long id, CarrilesModel carrilesModel){
        Optional<CarrilesModel> existing  = carrilesRepository.findById(id);
        if (existing.isPresent()) {
            CarrilesModel data = existing.get();
            data.setNombre(carrilesModel.getNombre());
            data.setSedesModel(carrilesModel.getSedesModel());
            return carrilesRepository.save(data);
        }
        return null;
    }

    public void eliminarCarril(Long id){
        carrilesRepository.deleteById(id);
    }
}
