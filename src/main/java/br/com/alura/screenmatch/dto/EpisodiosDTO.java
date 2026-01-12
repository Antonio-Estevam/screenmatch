package br.com.alura.screenmatch.dto;

import java.time.LocalDate;

public record EpisodiosDTO(
        String titulo,
        Integer temporada,
        Integer numeroEpisodio
) {
}
