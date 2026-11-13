package com.hotel.hotel_api.servicio.impl;

import com.hotel.hotel_api.dto.FacturaResponse;
import com.hotel.hotel_api.entidad.Factura;
import com.hotel.hotel_api.excepcion.FacturaNoEncontradaException;
import com.hotel.hotel_api.repositorio.RepositorioFactura;
import com.hotel.hotel_api.servicio.ServicioFactura;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioFacturaImpl implements ServicioFactura {

    private final RepositorioFactura repositorioFactura;

    @Override
    public FacturaResponse consultarPorId(Long id) {
        Factura factura = repositorioFactura.findById(id)
                .orElseThrow(() -> new FacturaNoEncontradaException(
                        "No existe una factura con id " + id));
        return Mapeador.aFacturaResponse(factura);
    }

    @Override
    public List<FacturaResponse> consultarTodas() {
        return repositorioFactura.findAllByOrderByIdAsc().stream()
                .map(Mapeador::aFacturaResponse)
                .toList();
    }
}