package com.icm.gestioncargaapi.validators;

import com.icm.gestioncargaapi.dto.ErrorResponse;
import com.icm.gestioncargaapi.models.CarrilesModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarrilesValidator {

    public ResponseEntity<?> validarDatos(CarrilesModel carrilesModel) {
        List<String> errores = new ArrayList<String>();
        if (carrilesModel.getNombre() == null || carrilesModel.getNombre().isEmpty()) {
            errores.add("El nombre del carril es obligatorio");
        }

        if (!errores.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Bad request", String.join("; ", errores));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
