package ir.mehdizebhi.ratingsdataservice.resources;

import ir.mehdizebhi.ratingsdataservice.models.Rating;
import ir.mehdizebhi.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataResource {

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating("1234", 4);
    }

    @GetMapping("/users/{userId}")
    public UserRating getUserRatingMovies(@PathVariable("userId") String userId){
        List<Rating> ratings = Arrays.asList(
                new Rating("550", 4),
                new Rating("100", 2),
                new Rating("551", 3),
                new Rating("534", 1)
        );
        return new UserRating(ratings);
    }

}
