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

    public List<CarrilesModel> findAll(){
        return carrilesRepository.findAll();
    }
    public Page<CarrilesModel> findAllPagened(Pageable pageable){
        return carrilesRepository.findAll(pageable);
    }

    public Optional<CarrilesModel> findById(Long id){
        return carrilesRepository.findById(id);
    }

    public List<CarrilesModel> findByEmpresaId(Long empresaId) {
        return carrilesRepository.findByEmpresasModelId(empresaId);
    }

    public CarrilesModel saveCarril(CarrilesModel carrilesModel){
        return carrilesRepository.save(carrilesModel);
    }

    public CarrilesModel updateCarril(Long id, CarrilesModel carrilesModel){
        Optional<CarrilesModel> existing  = carrilesRepository.findById(id);
        if (existing.isPresent()) {
            CarrilesModel data = existing.get();
            data.setNombre(carrilesModel.getNombre());
            data.setEmpresasModel(carrilesModel.getEmpresasModel());
            return carrilesRepository.save(data);
        }
        return null;
    }

    public void deleteCarril(Long id){
        carrilesRepository.deleteById(id);
    }
}
