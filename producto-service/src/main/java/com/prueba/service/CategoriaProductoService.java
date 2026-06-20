package com.prueba.service;

import com.prueba.exception.NotFoundException;
import com.prueba.model.CategoriaProducto;
import com.prueba.repository.CategoriaProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoriaProductoService {

    @Autowired
    private CategoriaProductoRepository categoriaRepository;

    public CategoriaProducto crear(CategoriaProducto request) {
        log.info("Creando nueva categoria: {}", request.getNombre());
        CategoriaProducto guardada = categoriaRepository.save(request);
        log.info("Categoria creada exitosamente con id: {}", guardada.getId());
        return guardada;
    }

    public List<CategoriaProducto> obtenerCategorias() {
        log.info("Obteniendo lista de todas las categorias");
        return categoriaRepository.findAll();
    }

    public CategoriaProducto obtenerCategoria(Long id) {
        log.info("Buscando categoria con id: {}", id);
        return categoriaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Categoria no encontrada con id: {}", id);
                    return new NotFoundException("Categoria no encontrada con id " + id);
                });
    }

    public void eliminar(Long id) {
        log.info("Eliminando categoria con id: {}", id);
        if (!categoriaRepository.existsById(id)) {
            log.warn("Categoria no encontrada con id: {}", id);
            throw new NotFoundException("Categoria no encontrada con id " + id);
        }
        categoriaRepository.deleteById(id);
        log.info("Categoria con id: {} eliminada exitosamente", id);
    }
}
