package com.icm.gestioncargaapi.validators;

import com.icm.gestioncargaapi.dto.ErrorResponse;
import com.icm.gestioncargaapi.models.RegistroCargasModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RegistroCargasValidator {

    public ResponseEntity<?> validarDatos(RegistroCargasModel registroCargas) {
        List<String> errores = new ArrayList<>();

        if (registroCargas.getHoraInicio() == null) {
            errores.add("La hora de inicio es obligatoria");
        }

        if (registroCargas.getHoraFin() == null) {
            errores.add("La hora de fin es obligatoria");
        }

        if (registroCargas.getTiempoCarga() == null) {
            errores.add("El tiempo de carga es obligatorio");
        }

        if (registroCargas.getDiaCarga() == null) {
            errores.add("El día de carga es obligatorio");
        }

        if (registroCargas.getCarrilesModel() == null) {
            errores.add("El carril es obligatorio");
        }

        if (!errores.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Bad request", String.join("; ", errores));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}
