package ir.mehdizebhi.moviecatalogservice.services;

import ir.mehdizebhi.moviecatalogservice.models.Rating;
import ir.mehdizebhi.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RatingApi {

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Value("${rating.url}")
    private String ratingUrl;

    public List<Rating> getRatings(String userId){
        return restTemplate.getForObject(ratingUrl + "ratingsdata/users/" + userId, UserRating.class)
                .getUserRatings();
    }


}
