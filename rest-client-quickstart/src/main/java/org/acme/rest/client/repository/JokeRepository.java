package org.acme.rest.client.repository;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.acme.rest.client.entity.RandomJoke;
import org.acme.rest.client.service.RandomJokeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class JokeRepository {

    @PersistenceContext
    EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(RandomJokeService.class);

    @Transactional
    public boolean create(RandomJoke joke) {
        try {
            if(find(joke.getId())!=null) {
                em.persist(joke);
            return true;}
            else
                return false;
        } catch (Exception e) {
            logger.error("Failed to save joke: {}", e.getMessage());
            // Handle the exception, e.g., by throwing a custom exception
        }
        return false;
    }
    @Transactional
    public RandomJoke find(Long id) {
        return em.find(RandomJoke.class, id);
    }
    @Transactional
    public List<RandomJoke> listAll() {
        return em.createQuery("SELECT j FROM RandomJoke j", RandomJoke.class).getResultList();
    }
}
