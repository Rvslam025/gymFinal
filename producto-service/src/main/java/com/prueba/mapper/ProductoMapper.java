package com.prueba.mapper;

import org.springframework.stereotype.Component;

import com.prueba.dto.ProductoRequest;
import com.prueba.dto.ProductoResponse;
import com.prueba.model.Producto;

@Component
public class ProductoMapper {

    public Producto dtoToObject(ProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setDisponible(request.getDisponible());
        // categoria se asigna en el service
        return producto;
    }

    public ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.isDisponible(),
                producto.getCategoria() != null ? producto.getCategoria().getNombre() : null
        );
    }
}
