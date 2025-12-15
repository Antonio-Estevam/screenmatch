package br.com.devfilho.screenmatch;

import br.com.devfilho.screenmatch.model.DataEpisode;
import br.com.devfilho.screenmatch.model.DataSeason;
import br.com.devfilho.screenmatch.model.DataTitle;
import br.com.devfilho.screenmatch.service.ConsumingApi;
import br.com.devfilho.screenmatch.service.DataConvert;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) { SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var searchTitle = new ConsumingApi();
		var json = searchTitle.getData("https://www.omdbapi.com/?t=gilmore+girls&Season=1&apikey=42d4452");
		System.out.println(json);

		DataConvert convert = new DataConvert();
		DataTitle data = convert.getData(json, DataTitle.class);
		System.out.println(data);

		json = searchTitle.getData("https://www.omdbapi.com/?t=gilmore+girls&Season=1&episode=2&apikey=42d4452");
		DataEpisode dataEpisode = convert.getData(json,DataEpisode.class);
		System.out.println(dataEpisode);

		List<DataSeason> seasons = new ArrayList<>();

		for (int i = 1; i <= data.totalSeasons(); i++){
			json = searchTitle.getData("https://www.omdbapi.com/?t=gilmore+girls&Season="+i+"&apikey=42d4452");
			DataSeason dataSeason = convert.getData(json,DataSeason.class);
			seasons.add(dataSeason);
		}
			seasons.forEach(System.out::println);
 	}
}
