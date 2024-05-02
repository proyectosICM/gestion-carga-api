package com.icm.gestioncargaapi.services;

import com.icm.gestioncargaapi.models.CarrilesModel;
import com.icm.gestioncargaapi.models.RegistroCargasModel;
import com.icm.gestioncargaapi.repositories.RegistroCargasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RegistroCargasService {
    @Autowired
    private RegistroCargasRepository registroCargasRepository;
    public long countByDiaCarga(LocalDate diaCarga) {
        return registroCargasRepository.countByDiaCarga(diaCarga);
    }

    public List<RegistroCargasModel> findByCarrilId(Long carrilId) {
        return registroCargasRepository.findByCarrilId(carrilId);
    }

    public List<RegistroCargasModel> findByDiaCargaAndCarrilId(LocalDate diaCarga, Long carrilId) {
        return registroCargasRepository.findByDiaCargaAndCarrilId(diaCarga, carrilId);
    }

    public List<RegistroCargasModel> listarRegistros(){
        return registroCargasRepository.findAll();
    }
    public Page<RegistroCargasModel> listarRegistrosPaginado(Pageable pageable){
        return registroCargasRepository.findAll(pageable);
    }



    public Optional<RegistroCargasModel> listarRegistroId(Long id) {
        return registroCargasRepository.findById(id);
    }

    public RegistroCargasModel crearRegistroCarga(RegistroCargasModel registroCargasModel){
        return registroCargasRepository.save(registroCargasModel);
    }

    public RegistroCargasModel editarRegistroCarga(Long id, RegistroCargasModel registroCargas){
        Optional<RegistroCargasModel> existing = registroCargasRepository.findById(id);
        if (existing.isPresent()) {
            RegistroCargasModel data = existing.get();
            data.setHoraInicio(registroCargas.getHoraInicio());
            data.setHoraFin(registroCargas.getHoraFin());
            data.setTiempoCarga(registroCargas.getTiempoCarga());
            data.setDiaCarga(registroCargas.getDiaCarga());
            data.setCarrilesModel(registroCargas.getCarrilesModel());

            return registroCargasRepository.save(data);
        }
        return null;
    }

    public void eliminarRegistroCarga(Long id){
        registroCargasRepository.deleteById(id);
    }
}
