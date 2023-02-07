package ir.mehdizebhi.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import ir.mehdizebhi.moviecatalogservice.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieApi {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${movie.url}")
    private String movieUrl;

    @HystrixCommand(fallbackMethod = "getFallbackMovie")
    public Movie getMovie(String movieId){
        return restTemplate
                          .getForObject(movieUrl + "/movies/" + movieId, Movie.class);
    }

    public Movie getFallbackMovie(String movieId){
        return new Movie("0", "No Title", "No Status", "0", "No Desc");
    }



}
