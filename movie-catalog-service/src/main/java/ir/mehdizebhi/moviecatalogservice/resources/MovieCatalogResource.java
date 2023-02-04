package ir.mehdizebhi.moviecatalogservice.resources;

import ir.mehdizebhi.moviecatalogservice.models.CatalogItem;
import ir.mehdizebhi.moviecatalogservice.models.Movie;
import ir.mehdizebhi.moviecatalogservice.models.Rating;
import ir.mehdizebhi.moviecatalogservice.services.MovieApi;
import ir.mehdizebhi.moviecatalogservice.services.RatingApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private WebClient.Builder webBuilder;

    @Autowired
    private RatingApi ratingApi;

    @Autowired
    private MovieApi movieApi;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        List<Rating> ratings = ratingApi.getRatings(userId);

        return ratings.stream().map(rating -> {
                    // get movie from another api for each movieId in ratings
                    Movie movie = movieApi.getMovie(rating.getMovieId());
                    // convert movie and rating to catalog
                    return new CatalogItem(movie, rating);
                }
        ).collect(Collectors.toList());
    }


//    @GetMapping("/{userId}")
//    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
//
////        List<Rating> ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/" + userId,
////                ParameterizedType<List<Rating>>() {})
//
//        List<Rating> ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class)
//                .getUserRatings();
//
//        return ratings.stream().map(rating -> {
//
//                    // get movie from another api for each movieId in ratings
//                    Movie movie = restTemplate
//                            .getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
//            /*
//            Movie movie = webBuilder.build()
//                    .get()
//                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();
//
//             */
//                    // convert movie and rating to catalog
//                    return new CatalogItem(movie, rating);
//                }
//        ).collect(Collectors.toList());
//
//
//    }

}
