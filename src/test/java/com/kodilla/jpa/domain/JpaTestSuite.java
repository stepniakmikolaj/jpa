package com.kodilla.jpa.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class JpaTestSuite {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    void shouldPersistCustomer() {
        //Given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer kodilla = new Customer(null, "Kodilla", "Raclawicka 13", "Wroclaw");

        //When
        entityManager.getTransaction().begin();
        entityManager.persist(kodilla);
        entityManager.flush();
        entityManager.getTransaction().commit();

        //Then
        Long key = kodilla.getId();
        Customer readCustomer = entityManager.find(Customer.class, key);
        entityManager.close();
        assertThat(readCustomer.getName()).isEqualTo(kodilla.getName());
    }
}