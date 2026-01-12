package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.EpisodiosDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService servico;


    @GetMapping
    public List<SerieDTO> obterSeries(){
       return servico.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obtertopCincoSeries(){
       return servico.obtertopCincoSeries();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos(){
       return servico.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id){
       return servico.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodiosDTO> obrterTodoasTemporadas(@PathVariable Long id){
       return servico.obrterTodoasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodiosDTO> obrterTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numero){
       return servico.obrterTemporadasPorNumero(id,numero);
    }

    @GetMapping("/categoria/{nomeGenero}")
    public List<SerieDTO> obrterTemporadasPorNumero(@PathVariable String nomeGenero){
       return servico.obrterSeriesPorCategoria(nomeGenero);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodiosDTO> obrterTemporadasPorNumero(@PathVariable Long id){
       return servico.obrterTopCincoTemporadas(id);
    }
}
