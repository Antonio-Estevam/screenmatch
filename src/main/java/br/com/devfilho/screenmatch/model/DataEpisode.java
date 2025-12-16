package br.com.devfilho.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DataEpisode(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") Integer episode,
        @JsonAlias("imdbRating") String score,
        @JsonAlias("Released") String released
) {
}
