package org.acme.rest.client.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.rest.client.repository.JokeRepository;
import org.acme.rest.client.dto.JokeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.IntStream;

@ApplicationScoped
public class RandomJokeService {


    @Inject
    RestClientCallService restClientCallService;

    private static final Logger logger = LoggerFactory.getLogger(RandomJokeService.class);

    @Inject
    JokeRepository jokeRepository;

    public List<List<JokeDTO>> fetchJokes(int count)
    {logger.info("abc");
        final int batches = (int) Math.ceil((double) count / 10);

        // Fetch the Uni<Extension> from the service
        final List<List<JokeDTO>> list = IntStream.range(0, batches)
                .mapToObj(i -> {
                    int batchSize = Math.min(10, count - i * 10);
                    try {
                        List<JokeDTO> joke = restClientCallService.fetchBasedOnBatch(batchSize);
                        logger.info("count {}"+jokeRepository.find(joke.get(0).getId()));
                        return joke;
                    }catch (Exception exception){
                        logger.error(exception.getMessage());
                    }
                    return null;
                })
                .toList(); {

        return list;
        }
          //  return uniList;       }
    }
}
