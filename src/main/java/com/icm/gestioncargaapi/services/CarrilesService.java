package com.icm.gestioncargaapi.services;

import com.icm.gestioncargaapi.models.CarrilesModel;
import com.icm.gestioncargaapi.repositories.CarrilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarrilesService {

    @Autowired
    CarrilesRepository carrilesRepository;

    public List<CarrilesModel> listarTodos(){
        return carrilesRepository.findAll();
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
            return carrilesRepository.save(data);
        }
        return null;
    }

    public void eliminarCarril(Long id){
        carrilesRepository.deleteById(id);
    }
}
