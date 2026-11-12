package com.hotel.hotel_api.entidad;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "factura")
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder


public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hospedaje_id", nullable = false, unique = true, updatable = false)
    private Hospedaje hospedaje;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaGeneracion;

    @Column(nullable = false, updatable = false)
    private Integer cantidadNoches;

    @Column(nullable = false, updatable = false, precision = 12, scale = 2)
    private BigDecimal valorNoche;

    @Column(nullable = false, updatable = false, precision = 14, scale = 2)
    private BigDecimal ValorTotal;
}
