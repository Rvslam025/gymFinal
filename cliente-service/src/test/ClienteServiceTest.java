package com.prueba.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.dto.ClienteRequest;
import com.prueba.dto.ClienteResponse;
import com.prueba.exception.ConflictException;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.ClienteMapper;
import com.prueba.model.Cliente;
import com.prueba.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    // Usamos @Spy en el Mapper para que use la lógica real de conversión sin tener que simularla
    @Spy
    private ClienteMapper clienteMapper = new ClienteMapper();

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteRequest request;

    @BeforeEach
    void setUp() {
        // Inicializar objeto simulado de Base de Datos
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setEmail("juan.perez@gmail.com");
        cliente.setTelefono("+56912345678");
        cliente.setFechaNacimiento(new Date());
        cliente.setActivo(true);

        // Inicializar objeto de petición (Request)
        request = new ClienteRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setEmail("juan.perez@gmail.com");
        request.setTelefono("+56912345678");
        request.setFechaNacimiento(new Date());
        request.setActivo(true);
    }

    @Test
    @DisplayName("Debe crear un cliente de forma exitosa")
    void testCrearClienteExitoso() {
        // GIVEN: El email no existe y el repositorio guarda bien
        when(clienteRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // WHEN: Llamamos al método crear
        ClienteResponse response = clienteService.crear(request);

        // THEN: Validamos resultados
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("juan.perez@gmail.com", response.getEmail());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Debe lanzar ConflictException al crear si el email ya existe")
    void testCrearClienteErrorEmailDuplicado() {
        // GIVEN: El email ya existe en el sistema
        when(clienteRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // WHEN & THEN: Se espera que falle con ConflictException
        assertThrows(ConflictException.class, () -> {
            clienteService.crear(request);
        });

        // Asegura que nunca se intentó guardar en la base de datos
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Debe listar todos los clientes registrados")
    void testObtenerClientes() {
        // GIVEN: El repositorio retorna una lista con un cliente
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));

        // WHEN: Listamos
        List<ClienteResponse> resultado = clienteService.obtenerClientes();

        // THEN: Evaluamos
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debe buscar un cliente por ID de forma exitosa")
    void testObtenerClientePorIdExitoso() {
        // GIVEN: El ID existe en la BD
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // WHEN: Buscamos
        ClienteResponse response = clienteService.obtenerCliente(1L);

        // THEN: Comprobamos
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException si el ID buscado no existe")
    void testObtenerClientePorIdNoEncontrado() {
        // GIVEN: El ID no existe
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NotFoundException.class, () -> {
            clienteService.obtenerCliente(99L);
        });
    }

    @Test
    @DisplayName("Debe modificar los datos de un cliente exitosamente")
    void testModificarClienteExitoso() {
        // GIVEN: El cliente existe y no hay conflicto de email
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // WHEN: Modificamos
        ClienteResponse response = clienteService.modificar(1L, request);

        // THEN: Comprobamos
        assertNotNull(response);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("Debe desactivar (soft-delete) un cliente al eliminarlo")
    void testEliminarClienteDesactivacion() {
        // GIVEN: El cliente existe activo
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // WHEN: Ejecutamos el método eliminar
        clienteService.eliminar(1L);

        // THEN: Comprobamos que pasó a falso (Inactivo) y se guardó
        assertFalse(cliente.getActivo());
        verify(clienteRepository, times(1)).save(cliente);
    }
}