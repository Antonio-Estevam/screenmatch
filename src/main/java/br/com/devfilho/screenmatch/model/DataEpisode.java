package br.com.devfilho.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DataEpisode(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") String episode,
        @JsonAlias("imdbRating") String score,
        @JsonAlias("Released") String Released
) {
}
