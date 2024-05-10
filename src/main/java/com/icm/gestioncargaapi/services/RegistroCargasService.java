package com.icm.gestioncargaapi.services;

import com.icm.gestioncargaapi.models.CarrilesModel;
import com.icm.gestioncargaapi.models.RegistroCargasModel;
import com.icm.gestioncargaapi.repositories.RegistroCargasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RegistroCargasService {
    @Autowired
    private RegistroCargasRepository registroCargasRepository;

    /**
     * Estadisticas y conteos
     */

    /* Conteo de cargas en un dia de todos los carriles  */
    public List<Map<String, Object>> groupByCarrilAndDiaCargaAndCount() {
        LocalDate fechaLocal = LocalDate.now();
        List<Map<String, Object>> results = registroCargasRepository.groupByCarrilAndDiaCargaAndCount(fechaLocal);

        List<Map<String, Object>> response = new ArrayList<>();
        for (Map<String, Object> result : results) {
            LocalDate fecha = (LocalDate) result.get("fecha");
            int carrilId = ((Number) result.get("carrilId")).intValue();
            String carrilNombre = (String) result.get("carrilNombre");
            long cantidad = (long) result.get("cantidad");

            Map<String, Object> entry = new HashMap<>();
            entry.put("fecha", Arrays.asList(fecha.getYear(), fecha.getMonthValue(), fecha.getDayOfMonth()));
            entry.put("cantidad", cantidad);
            entry.put("carrilId", carrilId);
            entry.put("carrilNombre", carrilNombre);
            response.add(entry);
        }

        return response;
    }

    /* Conteo de cargas en un dia de un carril */
    public Map<String, Object> groupByDiaCargaAndCount(Long carrilId) {
        LocalDate fechaLocal = LocalDate.now();
        return registroCargasRepository.groupByDiaCargaAndCount(carrilId, fechaLocal);
    }

    /**
     *  Registros Listados y Paginado
     */

    public List<RegistroCargasModel> findByCarrilId(Long carrilId) {
        return registroCargasRepository.findByCarrilId(carrilId);
    }

    public Page<RegistroCargasModel> findByCarrilIdOrderByDesc(Long carrilId, Pageable pageRequest) {
        return registroCargasRepository.findByCarrilIdOrderByDesc(carrilId, pageRequest);
    }

    public List<Map<String, Object>> obtenerRegistrosPorCarrilYDia(Long carrilId) {
        // Obtener la fecha actual y la fecha de hace 7 días
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicio = fechaActual.minusDays(6);
        LocalDate fechaFin = fechaActual;

        // Obtener los registros contados por día para el carril dado en los últimos 7 días
        List<Map<String, Object>> registrosPorDia = registroCargasRepository.countByDiaCarga(carrilId, fechaInicio, fechaFin);

        // Formatear los resultados según el formato deseado
        List<Map<String, Object>> resultadoFormateado = new ArrayList<>();
        LocalDate fecha = fechaInicio;

        // Inicializar un mapa con ceros para todos los días de los últimos 7 días
        Map<LocalDate, Integer> mapDias = new HashMap<>();
        while (!fecha.isAfter(fechaFin)) {
            mapDias.put(fecha, 0);
            fecha = fecha.plusDays(1);
        }

        // Actualizar el conteo de registros en los días existentes
        for (Map<String, Object> registro : registrosPorDia) {
            LocalDate fechaRegistro = (LocalDate) registro.get("fecha");
            Integer cantidad = ((Number) registro.get("cantidad")).intValue();
            mapDias.put(fechaRegistro, cantidad);
        }

        // Formatear los resultados
        for (Map.Entry<LocalDate, Integer> entry : mapDias.entrySet()) {
            Map<String, Object> dia = new HashMap<>();
            dia.put("fecha", new int[]{entry.getKey().getYear(), entry.getKey().getMonthValue(), entry.getKey().getDayOfMonth()});
            dia.put("cantidad", entry.getValue());
            resultadoFormateado.add(dia);
        }

        return resultadoFormateado;
    }

    public List<Map<String, Object>> obtenerRegistrosPorCarrilYDia() {
        // Obtener la fecha actual y la fecha de hace 7 días
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicio = fechaActual.minusDays(6);
        LocalDate fechaFin = fechaActual;

        // Obtener los registros contados por carril y día en los últimos 7 días
        List<Map<String, Object>> registros = registroCargasRepository.countByCarrilAndDiaCarga(fechaInicio, fechaFin);

        // Organizar los registros por carril y rellenar los días faltantes con cantidad 0
        Map<Long, List<Map<String, Object>>> registrosPorCarril = new HashMap<>();
        registros.forEach(registro -> {
            Long carrilId = (Long) registro.get("carrilId");
            if (!registrosPorCarril.containsKey(carrilId)) {
                registrosPorCarril.put(carrilId, new ArrayList<>());
            }
            registrosPorCarril.get(carrilId).add(registro);
        });

        // Completar los días faltantes con cantidad 0
        registrosPorCarril.forEach((carrilId, registrosCarril) -> {
            LocalDate fecha = fechaInicio;
            Set<LocalDate> fechasPresentes = registrosCarril.stream()
                    .map(registro -> (LocalDate) registro.get("fecha"))
                    .collect(Collectors.toSet());
            while (!fecha.isAfter(fechaFin)) {
                if (!fechasPresentes.contains(fecha)) {
                    Map<String, Object> registroVacio = new HashMap<>();
                    registroVacio.put("carrilId", carrilId);
                    registroVacio.put("fecha", fecha);
                    registroVacio.put("cantidad", 0);
                    registrosCarril.add(registroVacio);
                }
                fecha = fecha.plusDays(1);
            }
        });

        // Ordenar los registros por fecha
        registrosPorCarril.values().forEach(registrosCarril ->
                registrosCarril.sort(Comparator.comparing(registro -> (LocalDate) registro.get("fecha"))));

        // Organizar los resultados en el formato deseado
        return registrosPorCarril.values().stream()
                .map(registrosCarril -> {
                    Map<String, Object> resultadoCarril = new HashMap<>();
                    resultadoCarril.put("carrilId", registrosCarril.get(0).get("carrilId"));
                    resultadoCarril.put("dias", registrosCarril);
                    return resultadoCarril;
                })
                .collect(Collectors.toList());
    }

    /**
     * CRUD
     */

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
