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
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.client.ClienteClient;
import com.prueba.dto.ClienteResponse;
import com.prueba.dto.ReservaRequest;
import com.prueba.dto.ReservaResponse;
import com.prueba.exception.BusinessException;
import com.prueba.exception.ConflictException;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.ReservaMapper;
import com.prueba.model.Reserva;
import com.prueba.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClienteClient clienteClient; // ¡Crucial! Inyectamos el Feign Client simulado

    @Spy
    private ReservaMapper reservaMapper = new ReservaMapper(); // Usamos Spy para que haga el mapeo real

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reserva;
    private ReservaRequest request;
    private ClienteResponse clienteActivo;
    private ClienteResponse clienteInactivo;

    @BeforeEach
    void setUp() {
        // Objeto entidad (Base de datos simulada)
        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setClienteId(10L);
        reserva.setClaseId(5L);
        reserva.setEstado("CONFIRMADA");
        reserva.setFechaReserva(new Date());

        // Objeto de petición
        request = new ReservaRequest();
        request.setClienteId(10L);
        request.setClaseId(5L);
        request.setEstado("CONFIRMADA");
        request.setFechaReserva(new Date());

        // Objeto de respuesta del microservicio de Cliente (Activo)
        clienteActivo = new ClienteResponse();
        clienteActivo.setId(10L);
        clienteActivo.setActivo(true);

        // Objeto de respuesta del microservicio de Cliente (Inactivo)
        clienteInactivo = new ClienteResponse();
        clienteInactivo.setId(10L);
        clienteInactivo.setActivo(false);
    }

    @Test
    @DisplayName("Debe crear una reserva de manera exitosa")
    void testCrearReservaExitosa() {
        // GIVEN: El cliente existe/está activo, y la reserva no está duplicada
        when(clienteClient.obtenerClientePorId(10L)).thenReturn(clienteActivo);
        when(reservaRepository.existsByClienteIdAndClaseId(10L, 5L)).thenReturn(false);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        // WHEN
        ReservaResponse response = reservaService.crear(request);

        // THEN
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("CONFIRMADA", response.getEstado());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Debe lanzar BusinessException si al crear reserva el cliente no está activo")
    void testCrearReservaErrorClienteInactivo() {
        when(clienteClient.obtenerClientePorId(10L)).thenReturn(clienteInactivo);

        assertThrows(BusinessException.class, () -> reservaService.crear(request));
        
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Debe lanzar ConflictException si el cliente ya reservó la misma clase")
    void testCrearReservaErrorYaReservado() {
        when(clienteClient.obtenerClientePorId(10L)).thenReturn(clienteActivo);
        when(reservaRepository.existsByClienteIdAndClaseId(10L, 5L)).thenReturn(true);

        assertThrows(ConflictException.class, () -> reservaService.crear(request));
        
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Debe listar todas las reservas")
    void testObtenerReservas() {
        when(reservaRepository.findAll()).thenReturn(Arrays.asList(reserva));

        List<ReservaResponse> response = reservaService.obtenerReservas();

        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }

    @Test
    @DisplayName("Debe buscar una reserva por ID exitosamente")
    void testObtenerReservaPorIdExitosa() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        ReservaResponse response = reservaService.obtenerReserva(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException si la reserva buscada no existe")
    void testObtenerReservaPorIdNoEncontrada() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reservaService.obtenerReserva(99L));
    }

    @Test
    @DisplayName("Debe modificar la reserva de forma exitosa")
    void testModificarReservaExitosa() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(clienteClient.obtenerClientePorId(10L)).thenReturn(clienteActivo);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        ReservaResponse response = reservaService.modificar(1L, request);

        assertNotNull(response);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Debe lanzar BusinessException al modificar si el cliente está inactivo")
    void testModificarReservaErrorClienteInactivo() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(clienteClient.obtenerClientePorId(10L)).thenReturn(clienteInactivo);

        assertThrows(BusinessException.class, () -> reservaService.modificar(1L, request));
        
        verify(reservaRepository, never()).save(any(Reserva.class)); // Asegura que nunca se guardó
    }

    @Test
    @DisplayName("Debe eliminar una reserva correctamente")
    void testEliminarReservaExitosa() {
        when(reservaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reservaRepository).deleteById(1L);

        reservaService.eliminar(1L);

        verify(reservaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException al intentar eliminar una reserva que no existe")
    void testEliminarReservaNoEncontrada() {
        when(reservaRepository.existsById(99L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> reservaService.eliminar(99L));
        
        verify(reservaRepository, never()).deleteById(anyLong());
    }
}