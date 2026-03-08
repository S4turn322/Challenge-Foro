package com.alura.challenge_foro.domain.topico;

import com.alura.challenge_foro.domain.autor.Autor;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        Estatus estatus,
        String autor,
        String curso
) {

    public DatosDetalleTopico(Topico topico){

        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getEstatus(),
                topico.getAutor().getNombre(),
                topico.getCurso()
        );

    }

}
