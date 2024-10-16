package org.acme.rest.client.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.rest.client.dto.JokeDTO;
import org.acme.rest.client.entity.RandomJoke;
import org.acme.rest.client.repository.JokeRepository;
import org.acme.rest.client.restClient.JokeRestClientService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@ApplicationScoped
public class RestClientCallService {

    @RestClient
    JokeRestClientService jokeRestClientService;

    private static final Logger logger = LoggerFactory.getLogger(RandomJokeService.class);

    @Inject
    JokeRepository jokeRepository;

    public List<JokeDTO> fetchBasedOnBatch(int count) {
        List<JokeDTO> jokeUnis = IntStream.range(0, count)
                .mapToObj(i -> {
                    try {

                        // Uni<JokeDTO> joke = jokeRestClientService.getRandomJoke(count);
                        JokeDTO joke = jokeRestClientService.getById(count);
                        logger.info("joke " + joke.toString());
                        //  JokeDTO jokedto = joke.subscribeAsCompletionStage().toCompletableFuture().join();
                        logger.info("jokedto {}" + joke);
                        RandomJoke randomJoke = new RandomJoke(joke);
                        boolean a=jokeRepository.create(randomJoke);
                        logger.info(String.valueOf(a));
                        return joke;
                    } catch (Exception e) {
                        System.out.println("OOPS!!! something went wrong" + e.getMessage());
                    }
                    return null;
                }) // Calling the getRandomJoke() function 10 times
                .collect(Collectors.toList());
        return jokeUnis;
    }
}
