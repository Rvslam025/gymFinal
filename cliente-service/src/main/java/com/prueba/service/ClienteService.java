package com.prueba.service;

import com.prueba.dto.ClienteRequest;
import com.prueba.dto.ClienteResponse;
import com.prueba.exception.ConflictException;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.ClienteMapper;
import com.prueba.model.Cliente;
import com.prueba.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public ClienteResponse crear(ClienteRequest request) {
        log.info("Creando cliente con email: {}", request.getEmail());

        if (clienteRepository.existsByEmail(request.getEmail())) {
            log.warn("Intento de registro con email ya existente: {}", request.getEmail());
            throw new ConflictException("Ya existe un cliente registrado con el email: " + request.getEmail());
        }

        Cliente cliente = clienteMapper.dtoToObject(request);
        Cliente guardado = clienteRepository.save(cliente);
        log.info("Cliente creado correctamente con id: {}", guardado.getId());
        return clienteMapper.toResponse(guardado);
    }

    public List<ClienteResponse> obtenerClientes() {
        log.info("Obteniendo listado de clientes");
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    public ClienteResponse obtenerCliente(Long id) {
        log.info("Buscando cliente con id: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró el cliente con id: {}", id);
                    return new NotFoundException("No se encontró el cliente con id " + id);
                });
        return clienteMapper.toResponse(cliente);
    }

    public ClienteResponse modificar(Long id, ClienteRequest request) {
        log.info("Modificando cliente con id: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el cliente con id " + id));

        if (clienteRepository.existsByEmail(request.getEmail())
                && !cliente.getEmail().equals(request.getEmail())) {
            throw new ConflictException("El email ya está en uso por otro cliente");
        }

        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setFechaNacimiento(request.getFechaNacimiento());
        cliente.setActivo(request.getActivo());

        Cliente actualizado = clienteRepository.save(cliente);
        log.info("Cliente actualizado correctamente con id: {}", actualizado.getId());
        return clienteMapper.toResponse(actualizado);
    }

    public void eliminar(Long id) {
        log.info("Eliminando cliente con id: {}", id);
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException("No se encontró el cliente con id " + id);
        }
        clienteRepository.deleteById(id);
        log.info("Cliente eliminado correctamente con id: {}", id);
    }
}
