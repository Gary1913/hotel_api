package com.hotel.hotel_api.repositorio;

import com.hotel.hotel_api.entidad.Hospedaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioHospedaje extends JpaRepository<Hospedaje, Long> {

    // Check-In: ¿el cliente ya tiene un hospedaje activo?
    boolean existsByClienteIdAndActivoTrue(Long clienteId);

    // Check-Out: busca el hospedaje activo del cliente
    Optional<Hospedaje> findByClienteIdAndActivoTrue(Long clienteId);
}