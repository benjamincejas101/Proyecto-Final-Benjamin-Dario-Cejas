package com.ejemplo.articulos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplo.articulos.model.Articulo;
import com.ejemplo.articulos.repository.ArticuloRepository;

@Service
public class ArticuloServiceImpl implements ArticuloService {

    private final ArticuloRepository articuloRepository;

    @Autowired
    public ArticuloServiceImpl(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    @Override
    public List<Articulo> listarArticulos() {
        return articuloRepository.findAll();
    }

    @Override
    public Optional<Articulo> obtenerArticuloPorId(Long id) {
        return articuloRepository.findById(id);
    }

    @Override
    public Articulo guardarArticulo(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    @Override
    public Articulo actualizarArticulo(Long id, Articulo articulo) {

        // Buscar artículo existente
        Articulo existente = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

        // Actualizar campos CON LOS NOMBRES CORRECTOS
        existente.setNombre(articulo.getNombre());
        existente.setPrecio(articulo.getPrecio());
        existente.setImagen(articulo.getImagen());

        // Guardar cambios
        return articuloRepository.save(existente);
    }

    @Override
    public void eliminarArticulo(Long id) {
        if (!articuloRepository.existsById(id)) {
            throw new RuntimeException("Artículo no encontrado");
        }

        articuloRepository.deleteById(id);
    }
}