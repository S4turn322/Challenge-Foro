package com.alura.challenge_foro.domain.topico;

import com.alura.challenge_foro.domain.autor.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(@NotNull Long id,
                                  @NotBlank String autor,
                                  @NotBlank String titulo,
                                  @NotBlank String mensaje,
                                  @NotBlank String curso
                                  ) {
}
