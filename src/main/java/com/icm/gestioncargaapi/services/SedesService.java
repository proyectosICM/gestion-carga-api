package com.icm.gestioncargaapi.services;

import com.icm.gestioncargaapi.models.EmpresasModel;
import com.icm.gestioncargaapi.models.SedesModel;
import com.icm.gestioncargaapi.repositories.EmpresasRepository;
import com.icm.gestioncargaapi.repositories.SedesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SedesService {
    @Autowired
    private SedesRepository sedesRepository;

    public List<SedesModel> getSedesByEmpresaId(Long empresaId) {
        return sedesRepository.findByEmpresasModelId(empresaId);
    }

    public List<SedesModel> getAll(){
        return sedesRepository.findAll();
    }

    public Optional<SedesModel> getById(Long id){
        return sedesRepository.findById(id);
    }

    public SedesModel save(SedesModel sedesModel){
        return sedesRepository.save(sedesModel);
    }

    public SedesModel update(Long id, SedesModel sedesModel) {
        Optional<SedesModel> existingSede = sedesRepository.findById(id);

        if (existingSede.isPresent()) {
            SedesModel sede = existingSede.get();
            sede.setNombre(sedesModel.getNombre());
            sede.setEmpresasModel(sedesModel.getEmpresasModel());
            // Actualiza otros campos según sea necesario

            return sedesRepository.save(sede);
        } else {
            throw new RuntimeException("Sede no encontrada con el id " + id);
        }
    }
    public void deleteById(Long id){
        sedesRepository.deleteById(id);
    }
}
