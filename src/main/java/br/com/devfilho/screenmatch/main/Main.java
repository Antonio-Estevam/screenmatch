package br.com.devfilho.screenmatch.main;

import br.com.devfilho.screenmatch.model.DataEpisode;
import br.com.devfilho.screenmatch.model.DataSeason;
import br.com.devfilho.screenmatch.model.DataTitle;
import br.com.devfilho.screenmatch.model.Episode;
import br.com.devfilho.screenmatch.service.ConsumingApi;
import br.com.devfilho.screenmatch.service.DataConvert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final String BASEURL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=42d4452";

    private  Scanner input = new Scanner(System.in);
    private ConsumingApi searchTitle = new ConsumingApi();
    private DataConvert convert = new DataConvert();

    public void showMenu() {
        System.out.println("Digite o nome da série.");
        var sereieName = input.nextLine();
        sereieName = sereieName.replace(" ", "+");
        var json = searchTitle.getData(BASEURL + sereieName + API_KEY);
        DataTitle data = convert.getData(json, DataTitle.class);
        System.out.println(data);

        List<DataSeason> seasons = new ArrayList<>();

        for (int i = 1; i <= data.totalSeasons(); i++) {
            json = searchTitle.getData(BASEURL + sereieName + "&season=" + i + API_KEY);
            DataSeason dataSeason = convert.getData(json, DataSeason.class);
            seasons.add(dataSeason);
        }
        seasons.forEach(System.out::println);
        //seasons.forEach(t -> t.episode().forEach(e -> System.out.println(e.title())));/* for (int i = 0; i < data.totalSeasons(); i++) {
        //            List<DataEpisode> episodesSeason = new ArrayList<>();
        //            episodesSeason = seasons.get(i).episode();
        //            for (int j = 0; j < episodesSeason.size(); j++) {
        //                System.out.println(episodesSeason.get(j).title());
        //            }
        //        }
        List<DataEpisode> dataEpisodes = seasons.stream()
                .flatMap(t -> t.episode().stream())
                .collect(Collectors.toUnmodifiableList());

        System.out.println("\n Top 10 episódios");
        dataEpisodes.stream()
                .filter(e -> !e.score().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println(" :Primeiro Filtro (N/A): "+ e))
                .sorted(Comparator.comparing(DataEpisode::score).reversed())
                .peek(e -> System.out.println(" :Ordenação: "+ e))
                .limit(10)
                .peek(e -> System.out.println(" :Limite: "+ e))
                .map(e -> e.title().toUpperCase())
                .peek(e -> System.out.println(" :Mapeamento: "+ e))
                .forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(t -> t.episode().stream()
                        .map(d -> new Episode(t.numberOfEpsode(), d))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("Digite um trecho do titolo.");
        var partOfTheTitle = input.nextLine();


        Optional<Episode> episodeSerch = episodes.stream()

                .filter(e -> e.getTitle().toUpperCase().contains(partOfTheTitle.toUpperCase()))
                .findFirst();

        if(episodeSerch.isPresent()){
            System.out.println("Encontrado");
            System.out.println("Temporada "+episodeSerch.get().getSeason());
        }else {
            System.out.println("Episódio não encontrado! ");
        }

        System.out.println("A partir de que ano você desja ver os episódios?");
        var year = input.nextInt();
        input.nextLine();

        LocalDate dataSerch = LocalDate.of(year,1,1);

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodes.stream()
                .filter(e -> e.getReleased() != null && e.getReleased().isAfter(dataSerch))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getSeason() +
                        " Episódio: " + e.getEpisode() +
                        " Data de lançamento: " + e.getReleased().format(formater)
                ));


        Map<Integer, Double> evaluationBySeason = episodes.stream()
                .filter(e -> e.getScore() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getScore)));

        System.out.println(evaluationBySeason);

        DoubleSummaryStatistics est = episodes.stream()
                .filter(e -> e.getScore() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getScore));

        System.out.println("Média: "+ est.getAverage());
        System.out.println("Melhor episódio: "+ est.getMax());
        System.out.println("Pior episódio: "+ est.getMin());
        System.out.println("Quantidade: "+ est.getCount());
    }
}
