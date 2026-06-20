package com.prueba.mapper;

import com.prueba.dto.ClienteRequest;
import com.prueba.dto.ClienteResponse;
import com.prueba.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    
    public Cliente dtoToObject (ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setFechaNacimiento(request.getFechaNacimiento());
        cliente.setActivo(request.getActivo());
        return cliente;
    }

    public ClienteResponse toResponse(Cliente cliente) {
    return new ClienteResponse(
        cliente.getId(),
        cliente.getNombre(),
        cliente.getApellido(),
        cliente.getEmail(),
        cliente.getTelefono(),
        cliente.getFechaNacimiento(),
        cliente.getActivo()
    );
    }

}
