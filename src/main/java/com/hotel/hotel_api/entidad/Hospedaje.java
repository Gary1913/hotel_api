package com.hotel.hotel_api.entidad;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "hospedaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Hospedaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Habitacion habitacion;

    @Column(nullable = false)
    private LocalDateTime fechaIngreso;

    @Column(nullable = false)
    private LocalDateTime fechaSalida;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @OneToOne(mappedBy = "hospedaje")
    private Factura factura;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
