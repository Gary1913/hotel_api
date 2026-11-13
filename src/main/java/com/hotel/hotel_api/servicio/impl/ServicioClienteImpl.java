package com.hotel.hotel_api.servicio.impl;

import com.hotel.hotel_api.dto.ClienteRequest;
import com.hotel.hotel_api.dto.ClienteResponse;
import com.hotel.hotel_api.entidad.Cliente;
import com.hotel.hotel_api.excepcion.ClienteNoEncontradoException;
import com.hotel.hotel_api.excepcion.ClienteExisteException;
import com.hotel.hotel_api.repositorio.RepositorioCliente;
import com.hotel.hotel_api.servicio.ServicioCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioClienteImpl implements ServicioCliente {

    private final RepositorioCliente repositorioCliente;

    @Override
    @Transactional
    public ClienteResponse crearCliente(ClienteRequest request) {
        String identificacion = request.getIdentificacion().trim();

        if (repositorioCliente.existsByIdentificacion(identificacion)) {
            throw new ClienteExisteException(
                    "Ya existe un cliente registrado con la identificación " + identificacion);
        }

        Cliente cliente = Cliente.builder()
                .identificacion(identificacion)
                .nombreCompleto(request.getNombreCompleto().trim())
                .correoElectronico(request.getCorreoElectronico().trim().toLowerCase())
                .telefono(request.getTelefono().trim())
                .build();

        Cliente guardado = repositorioCliente.save(cliente);
        return Mapeador.aClienteResponse(guardado);
    }

    @Override
    public ClienteResponse consultarPorId(Long id) {
        Cliente cliente = repositorioCliente.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoException(
                        "No existe un cliente con id " + id));
        return Mapeador.aClienteResponse(cliente);
    }

    @Override
    public ClienteResponse consultarPorIdentificacion(String identificacion) {
        Cliente cliente = repositorioCliente.findByIdentificacion(identificacion.trim())
                .orElseThrow(() -> new ClienteNoEncontradoException(
                        "No existe un cliente con la identificación " + identificacion));
        return Mapeador.aClienteResponse(cliente);
    }

    @Override
    public List<ClienteResponse> consultarTodos() {
        return repositorioCliente.findAll(Sort.by("id")).stream()
                .map(Mapeador::aClienteResponse)
                .toList();
    }
}