package com.alura.challenge_foro.domain.topico;

import com.alura.challenge_foro.domain.autor.Autor;
import jakarta.persistence.*;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")

@Getter //Genera automáticamente los métodos "get" para todos los atributos
@NoArgsConstructor //Crea un constructor sin parámetros (vacío). JPA lo necesita obligatoriamente para funcionar
@AllArgsConstructor//Crea un constructor que recibe todos los atributos de la clase como parámetros.
@EqualsAndHashCode(of = "id")

public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String titulo;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaDeCreacion;
    @Enumerated(EnumType.STRING)
    private Estatus estatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String curso;

    public Topico(DatosRegistroTopico datos, Autor autor){

        this.id = null;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.curso = datos.curso();
        this.fechaDeCreacion = LocalDateTime.now();
        this.estatus = Estatus.ABIERTO;
        this.autor = autor;

    }

    public void ActualizarDatos(@Valid DatosActualizarTopico datos){

        if (datos.titulo() != null){
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null){
            this.mensaje = datos.mensaje();
        }
        if (datos.curso() != null){
            this.curso = datos.curso();
        }

    }


}
