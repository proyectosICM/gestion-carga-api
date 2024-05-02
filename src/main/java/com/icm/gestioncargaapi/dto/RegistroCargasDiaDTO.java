package com.icm.gestioncargaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroCargasDiaDTO {
    private LocalDate diaCarga;
    private Long totalRegistros;
}
