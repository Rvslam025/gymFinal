package com.prueba.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.exception.NotFoundException;
import com.prueba.dto.ProductoRequest;
import com.prueba.dto.ProductoResponse;
import com.prueba.mapper.ProductoMapper;
import com.prueba.model.CategoriaProducto;
import com.prueba.model.Producto;
import com.prueba.repository.CategoriaProductoRepository;
import com.prueba.repository.ProductoRepository;

@Slf4j
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaProductoRepository categoriaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    public ProductoResponse crear(ProductoRequest request) {
        log.info("Creando nuevo producto: {}", request.getNombre());

        CategoriaProducto categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con id " + request.getCategoriaId()));

        Producto producto = productoMapper.dtoToObject(request);
        producto.setCategoria(categoria);

        Producto guardado = productoRepository.save(producto);
        log.info("Producto creado exitosamente con id: {}", guardado.getId());
        return productoMapper.toResponse(guardado);
    }

    public List<ProductoResponse> obtenerProductos() {
        log.info("Obteniendo lista de todos los productos");
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    public ProductoResponse obtenerProducto(Long id) {
        log.info("Buscando producto con id: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Producto no encontrado con id: {}", id);
                    return new NotFoundException("No se encontro el producto con id " + id);
                });
        return productoMapper.toResponse(producto);
    }

    public ProductoResponse modificar(Long id, ProductoRequest request) {
        log.info("Modificando producto con id: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el producto con id " + id));

        CategoriaProducto categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con id " + request.getCategoriaId()));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setDisponible(request.getDisponible());
        producto.setCategoria(categoria);

        Producto actualizado = productoRepository.save(producto);
        log.info("Producto con id: {} actualizado exitosamente", id);
        return productoMapper.toResponse(actualizado);
    }

    public void eliminar(Long id) {
        log.info("Eliminando producto con id: {}", id);
        if (!productoRepository.existsById(id)) {
            throw new NotFoundException("No se encontro el producto con id " + id);
        }
        productoRepository.deleteById(id);
        log.info("Producto con id: {} eliminado exitosamente", id);
    }
}
