package com.hotel.hotel_api.entidad;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "habitacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer numeroHabitacion;

    @Column(nullable = false)
    private Integer piso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoHabitacion tipoHabitacion;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorNoche;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoHabitacion estado;

    @OneToMany(mappedBy = "habitacion")
    @Builder.Default
    private List<Hospedaje> hospedajes = new ArrayList<>();
}
