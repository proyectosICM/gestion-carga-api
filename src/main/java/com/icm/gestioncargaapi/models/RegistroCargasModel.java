package com.icm.gestioncargaapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registroCargas")
public class RegistroCargasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    @Column(nullable = false)
    private LocalTime tiempoCarga;

    @Column(nullable = false)
    private LocalDate diaCarga;

    @ManyToOne
    @JoinColumn(name = "carril", referencedColumnName = "id", nullable = false)
    private CarrilesModel carrilesModel;
}
