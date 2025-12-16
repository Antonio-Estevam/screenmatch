package br.com.devfilho.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer episode;
    private double score;
    private LocalDate released;

    public Episode(Integer season, DataEpisode dataEpisode) {
        this.season = season;
        this.title = dataEpisode.title();
        this.episode = dataEpisode.episode();
        try {
            this.score = Double.valueOf(dataEpisode.score());
        }catch (NumberFormatException ex){
            this.score = 0.0;
        }
        try {
            this.released = LocalDate.parse(dataEpisode.released());
        }catch (DateTimeParseException ex){
            this.released = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    @Override
    public String toString() {
        return  "season=" + season +
                ", title='" + title + '\'' +
                ", episode=" + episode +
                ", score=" + score +
                ", released=" + released ;
    }
}
