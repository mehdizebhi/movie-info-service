package ir.mehdizebhi.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import ir.mehdizebhi.moviecatalogservice.models.Rating;
import ir.mehdizebhi.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class RatingApi {

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Value("${rating.url}")
    private String ratingUrl;

    @HystrixCommand(fallbackMethod = "getFallbackRatings",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "70"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            })
    public List<Rating> getRatings(String userId) {
        return restTemplate.getForObject(ratingUrl + "ratingsdata/users/" + userId, UserRating.class)
                .getUserRatings();
    }

    public List<Rating> getFallbackRatings(String userId) {
        return Arrays.asList(new Rating("0", 0));
    }


}
