package com.hotel.hotel_api.repositorio;

import com.hotel.hotel_api.entidad.Factura;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface RepositorioFactura extends Repository<Factura, Long> {

    Factura save(Factura factura);

    @EntityGraph(attributePaths = {"hospedaje", "hospedaje.cliente", "hospedaje.habitacion"})
    Optional<Factura> findById(Long id);

    @EntityGraph(attributePaths = {"hospedaje", "hospedaje.cliente", "hospedaje.habitacion"})
    List<Factura> findAllByOrderByIdAsc();
}