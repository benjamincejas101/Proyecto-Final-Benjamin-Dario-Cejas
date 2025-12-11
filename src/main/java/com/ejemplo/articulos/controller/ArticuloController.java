package com.ejemplo.articulos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.articulos.model.Articulo;
import com.ejemplo.articulos.service.ArticuloService;

@RestController
@RequestMapping("/api/articulos")
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (Postman, front, etc.)
public class ArticuloController {

    private final ArticuloService articuloService;

    @Autowired
    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    // Obtener todos los artículos
    @GetMapping
    public List<Articulo> listarArticulos() {
        return articuloService.listarArticulos();
    }

    // Obtener un artículo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Articulo> obtenerArticulo(@PathVariable Long id) {
        return articuloService.obtenerArticuloPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo artículo
    @PostMapping
    public Articulo crearArticulo(@RequestBody Articulo articulo) {
        return articuloService.guardarArticulo(articulo);
    }

    // Actualizar un artículo existente
    @PutMapping("/{id}")
    public ResponseEntity<Articulo> actualizarArticulo(@PathVariable Long id, @RequestBody Articulo articulo) {
        try {
            Articulo actualizado = articuloService.actualizarArticulo(id, articulo);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un artículo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArticulo(@PathVariable Long id) {
        try {
            articuloService.eliminarArticulo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
