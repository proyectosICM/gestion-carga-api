package com.icm.gestioncargaapi.services;

import com.icm.gestioncargaapi.models.EmpresasModel;
import com.icm.gestioncargaapi.models.RegistroCargasModel;
import com.icm.gestioncargaapi.repositories.EmpresasRepository;
import com.icm.gestioncargaapi.repositories.RegistroCargasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresasServices {
    @Autowired
    private EmpresasRepository empresasRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<EmpresasModel> getAll() {
        return empresasRepository.findAll();
    }

    public Optional<EmpresasModel> getById(Long id) {
        return empresasRepository.findById(id);
    }

    public Optional<EmpresasModel> findByUsername(String username) {
        return empresasRepository.findByUsername(username);
    }

    public EmpresasModel save(EmpresasModel empresas) {
        EmpresasModel empresasModel = EmpresasModel.builder()
                .nombre(empresas.getNombre())
                .username(empresas.getUsername())
                .password(passwordEncoder.encode(empresas.getPassword()))
                .build();

        return empresasRepository.save(empresasModel);
    }

    public EmpresasModel update(Long id, EmpresasModel empresasModel) {
        Optional<EmpresasModel> existing  = empresasRepository.findById(id);

        if (existing .isPresent()) {
            EmpresasModel empresa = existing.get();

            empresa.setNombre(empresasModel.getNombre());
            empresa.setUsername(empresasModel.getUsername());
            empresa.setPassword(passwordEncoder.encode(empresasModel.getPassword()));

            return empresasRepository.save(empresa);
        } else {
            throw new RuntimeException("Empresa no encontrada con el id " + id);
        }
    }

    public void deleteById(Long id) {
        empresasRepository.deleteById(id);
    }
}
