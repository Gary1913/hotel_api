package com.hotel.hotel_api.entidad;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder



public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String identificacion;

    @Column(nullable = false, length = 150)
    private String nombreCompleto;

    @Column(nullable = false, length = 150)
    private String correoElectronico;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "cliente")
    @Builder.Default
    private List<Hospedaje> hospedajes = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
