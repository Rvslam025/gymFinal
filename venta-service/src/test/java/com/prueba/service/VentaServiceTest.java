package com.prueba.service;

import java.util.ArrayList;
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
import com.prueba.client.ProductoClient;
import com.prueba.dto.ClienteResponse;
import com.prueba.dto.ProductoResponse;
import com.prueba.dto.VentaRequest;
import com.prueba.dto.VentaResponse;
import com.prueba.exception.ConflictException;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.VentaMapper;
import com.prueba.model.DetalleVenta;
import com.prueba.model.Venta;
import com.prueba.repository.VentaRepository;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ProductoClient productoClient;

    @Mock
    private ClienteClient clienteClient;

    @Spy
    private VentaMapper ventaMapper = new VentaMapper(); // Usamos Spy para validar el mapeo real de la lista de detalles

    @InjectMocks
    private VentaService ventaService;

    private Venta venta;
    private VentaRequest request;
    private ProductoResponse productoValido;
    private ClienteResponse clienteValido;

    @BeforeEach
    void setUp() {
        // Inicializar ProductoResponse simulado (Con stock y disponible)
        productoValido = new ProductoResponse();
        productoValido.setId(5L);
        productoValido.setStock(10);
        productoValido.setDisponible(true);
        productoValido.setPrecio(100.0);

        // Inicializar ClienteResponse simulado
        clienteValido = new ClienteResponse();
        clienteValido.setId(10L);

        // Inicializar Request
        request = new VentaRequest();
        request.setClienteId(10L);
        request.setProductoId(5L);
        request.setCantidad(2); // Solicita 2 unidades

        // Inicializar Entidad Venta (Base de datos)
        venta = new Venta();
        venta.setId(1L);
        venta.setFechaVenta(new Date());
        venta.setCantidad(2);
        venta.setProductoId(5L);
        venta.setClienteId(10L);
        venta.setTotal(200.0);
        venta.setDetalles(new ArrayList<>()); // Evita NullPointerException en el Mapper

        // Inicializar DetalleVenta
        DetalleVenta detalle = new DetalleVenta();
        detalle.setId(1L);
        detalle.setProductoId(5L);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(100.0);
        detalle.setSubtotal(200.0);
        detalle.setVenta(venta);
        venta.getDetalles().add(detalle);
    }

    @Test
    @DisplayName("Debe crear una venta exitosamente cuando hay stock y está disponible")
    void testCrearVentaExitosa() {
        // GIVEN
        when(productoClient.obtenerProductoPorId(5L)).thenReturn(productoValido);
        when(clienteClient.obtenerClientePorId(10L)).thenReturn(clienteValido);
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);

        // WHEN
        VentaResponse response = ventaService.crear(request);

        // THEN
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(200.0, response.getTotal());
        assertFalse(response.getDetalles().isEmpty()); // Validamos que el mapper procesó los detalles
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }

    @Test
    @DisplayName("Debe lanzar ConflictException si no hay stock suficiente")
    void testCrearVentaErrorStockInsuficiente() {
        // GIVEN: El producto solo tiene 1 de stock, pero el request pide 2
        productoValido.setStock(1);
        when(productoClient.obtenerProductoPorId(5L)).thenReturn(productoValido);
        when(clienteClient.obtenerClientePorId(10L)).thenReturn(clienteValido);

        // WHEN & THEN
        assertThrows(ConflictException.class, () -> ventaService.crear(request));
        verify(ventaRepository, never()).save(any(Venta.class));
    }

    @Test
    @DisplayName("Debe lanzar ConflictException si el producto no está disponible")
    void testCrearVentaErrorProductoNoDisponible() {
        // GIVEN: El producto tiene stock pero fue marcado como no disponible
        productoValido.setDisponible(false);
        when(productoClient.obtenerProductoPorId(5L)).thenReturn(productoValido);
        when(clienteClient.obtenerClientePorId(10L)).thenReturn(clienteValido);

        // WHEN & THEN
        assertThrows(ConflictException.class, () -> ventaService.crear(request));
        verify(ventaRepository, never()).save(any(Venta.class));
    }

    @Test
    @DisplayName("Debe listar todas las ventas")
    void testObtenerVentas() {
        when(ventaRepository.findAll()).thenReturn(Arrays.asList(venta));

        List<VentaResponse> response = ventaService.obtenerVentas();

        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }

    @Test
    @DisplayName("Debe buscar una venta por ID exitosamente")
    void testObtenerVentaPorIdExitosa() {
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        VentaResponse response = ventaService.obtenerVenta(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException si la venta buscada no existe")
    void testObtenerVentaPorIdNoEncontrada() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ventaService.obtenerVenta(99L));
    }

    @Test
    @DisplayName("Debe eliminar una venta exitosamente")
    void testEliminarVentaExitosa() {
        when(ventaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(ventaRepository).deleteById(1L);

        ventaService.eliminar(1L);

        verify(ventaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException al intentar eliminar venta que no existe")
    void testEliminarVentaNoEncontrada() {
        when(ventaRepository.existsById(99L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> ventaService.eliminar(99L));
        verify(ventaRepository, never()).deleteById(anyLong());
    }
}