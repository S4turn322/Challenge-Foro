package com.alura.challenge_foro.controller;

import com.alura.challenge_foro.domain.autor.AutorRepository;
import com.alura.challenge_foro.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/topicos")
@RestController

public class TopicoController {


    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder){

        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())){
            return ResponseEntity.badRequest().body("Ya existe un topico con ese titulo y mensaje");
        }

        var autor = autorRepository.getReferenceById(datos.id());
        var topico = new Topico(datos, autor);
        topicoRepository.save(topico);

        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));

    }

    @Transactional
    @GetMapping
    public ResponseEntity<Page<DatosListarTopico>> listar(@PageableDefault(size = 10, sort = {"fechaDeCreacion"}) Pageable paginacion){

        var page = topicoRepository.findAll(paginacion).map(DatosListarTopico::new);
        return ResponseEntity.ok(page);


    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id){

        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));

    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datos){

        var topico = topicoRepository.findById(datos.id());

        if (topico.isPresent()) {
            var topicoActualizado = topico.get();

            if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
                return ResponseEntity.badRequest().body("Ya existe un tópico con ese título y mensaje");
            }
            topicoActualizado.ActualizarDatos(datos);
            return ResponseEntity.ok(new DatosDetalleTopico(topicoActualizado));
        }
        return ResponseEntity.notFound().build();

    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id){

        var topico = topicoRepository.findById(id);

        if (topico.isPresent()){

            topicoRepository.deleteById(id);

            return ResponseEntity.noContent().build();

        }

        return ResponseEntity.notFound().build();

    }



}
