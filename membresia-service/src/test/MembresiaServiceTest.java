package com.prueba.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.dto.MembresiaRequest;
import com.prueba.dto.MembresiaResponse;
import com.prueba.exception.BusinessException;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.MembresiaMapper;
import com.prueba.model.Membresia;
import com.prueba.model.TipoMembresia;
import com.prueba.repository.MembresiaRepository;
import com.prueba.repository.TipoMembresiaRepository;

@ExtendWith(MockitoExtension.class)
public class MembresiaServiceTest {

    @Mock
    private MembresiaRepository membresiaRepository;

    @Mock
    private TipoMembresiaRepository tipoMembresiaRepository; // Inyectado porque el servicio lo usa

    @Mock
    private MembresiaMapper membresiaMapper;

    @InjectMocks
    private MembresiaService membresiaService;

    private Membresia membresia;
    private TipoMembresia tipoMembresia;
    private MembresiaRequest request;
    private MembresiaResponse response;

    @BeforeEach
    void setUp() {
        // Inicializar Tipo de Membresia
        tipoMembresia = new TipoMembresia();
        tipoMembresia.setId(1L);
        tipoMembresia.setNombre("Premium");
        tipoMembresia.setDuracionDias(30);
        tipoMembresia.setPrecio(50.0);

        // Inicializar Membresia
        membresia = new Membresia();
        membresia.setId(1L);
        membresia.setClienteId(100L);
        membresia.setTipoMembresia(tipoMembresia);
        membresia.setFechaInicio(LocalDate.now());
        membresia.setFechaFin(LocalDate.now().plusDays(30));
        membresia.setActiva(true);

        // Inicializar Request simulado (solo asumimos que tiene clienteId y tipoMembresiaId)
        request = new MembresiaRequest();
        request.setClienteId(100L);
        request.setTipoMembresiaId(1L);

        // Inicializar Response simulado
        response = new MembresiaResponse();
        response.setId(1L);
        response.setActiva(true);
    }

    @Test
    @DisplayName("Debe crear membresía exitosamente")
    void testCrearExitoso() {
        when(membresiaRepository.existsByClienteId(100L)).thenReturn(false);
        when(tipoMembresiaRepository.findById(1L)).thenReturn(Optional.of(tipoMembresia));
        when(membresiaRepository.save(any(Membresia.class))).thenReturn(membresia);
        when(membresiaMapper.toResponse(any(Membresia.class))).thenReturn(response);

        MembresiaResponse resultado = membresiaService.crear(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(membresiaRepository, times(1)).save(any(Membresia.class));
    }

    @Test
    @DisplayName("Debe lanzar BusinessException si el cliente ya tiene membresía")
    void testCrearErrorClienteYaTieneMembresia() {
        when(membresiaRepository.existsByClienteId(100L)).thenReturn(true);

        assertThrows(BusinessException.class, () -> membresiaService.crear(request));
        
        verify(membresiaRepository, never()).save(any(Membresia.class));
    }

    @Test
    @DisplayName("Debe listar todas las membresías")
    void testListarMembresias() {
        when(membresiaRepository.findAll()).thenReturn(Arrays.asList(membresia));
        when(membresiaMapper.toResponse(any(Membresia.class))).thenReturn(response);

        List<MembresiaResponse> resultado = membresiaService.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe obtener membresía por ID")
    void testObtenerPorIdExitoso() {
        when(membresiaRepository.findById(1L)).thenReturn(Optional.of(membresia));
        when(membresiaMapper.toResponse(any(Membresia.class))).thenReturn(response);

        MembresiaResponse resultado = membresiaService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Debe modificar membresía exitosamente")
    void testModificarExitoso() {
        when(membresiaRepository.findById(1L)).thenReturn(Optional.of(membresia));
        when(tipoMembresiaRepository.findById(1L)).thenReturn(Optional.of(tipoMembresia));
        when(membresiaRepository.save(any(Membresia.class))).thenReturn(membresia);
        when(membresiaMapper.toResponse(any(Membresia.class))).thenReturn(response);

        MembresiaResponse resultado = membresiaService.modificar(1L, request);

        assertNotNull(resultado);
        verify(membresiaRepository, times(1)).save(membresia);
    }

    @Test
    @DisplayName("Debe eliminar membresía físicamente de la base de datos")
    void testEliminarExitoso() {
        when(membresiaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(membresiaRepository).deleteById(1L);

        membresiaService.eliminar(1L);

        verify(membresiaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException si se intenta eliminar una membresía que no existe")
    void testEliminarNoEncontrado() {
        when(membresiaRepository.existsById(99L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> membresiaService.eliminar(99L));
        
        verify(membresiaRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Debe validar que la membresía de un cliente está activa")
    void testValidarActivaTrue() {
        when(membresiaRepository.findByClienteId(100L)).thenReturn(Optional.of(membresia));

        Boolean resultado = membresiaService.validarActiva(100L);

        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe retornar false si el cliente no tiene membresía")
    void testValidarActivaFalse() {
        when(membresiaRepository.findByClienteId(99L)).thenReturn(Optional.empty());

        Boolean resultado = membresiaService.validarActiva(99L);

        assertFalse(resultado);
    }
}