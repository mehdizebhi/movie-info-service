package ir.mehdizebhi.movieinfoservice.services;


import ir.mehdizebhi.movieinfoservice.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieDBApi {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.url}")
    private String apiUrl;

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    public Movie getMovieInfo(String movieId){
        return restTemplate
                .getForObject(apiUrl + movieId + "?api_key=" + apiKey, Movie.class);
    }

}
