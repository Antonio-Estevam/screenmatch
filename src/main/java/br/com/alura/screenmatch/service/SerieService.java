package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodiosDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repositorio;

    public List<SerieDTO> obterTodasAsSeries(){
        return  converteDados(repositorio.findAll());
    }

    public List<SerieDTO> obtertopCincoSeries() {
        return  converteDados(repositorio.findTop5ByOrderByAvaliacaoDesc());

    }

    private List<SerieDTO> converteDados(List<Serie>series){
        return series.stream()
                .map(s-> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(repositorio.findTopLancamentosPorData());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        Serie s = serie.get();

        if (serie.isPresent()){
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse());
        }else {
            return null;
        }
    }

    public List<EpisodiosDTO> obrterTodoasTemporadas(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        Serie s = serie.get();

        if (serie.isPresent()){
            return s.getEpisodios().stream()
                    .map(e-> new EpisodiosDTO(e.getTitulo(),e.getTemporada(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());


        }else {
            return null;
        }
    }

    public List<EpisodiosDTO> obrterTemporadasPorNumero(Long id, Long numero) {
            return repositorio.obterEpisodiosPorTemporada(id, numero).stream()
                    .map(e-> new EpisodiosDTO(e.getTitulo(),e.getTemporada(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
    }

    public List<SerieDTO> obrterSeriesPorCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        return converteDados(repositorio.findByGenero(categoria));
    }

    public List<EpisodiosDTO> obrterTopCincoTemporadas(Long id) {
        Optional<Serie> serie = repositorio.findById(id);

        return repositorio.topEpisodiosPorSerie(serie.get()).stream()
                .map(e-> new EpisodiosDTO(e.getTitulo(),e.getTemporada(), e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }
}
