package com.integration.tests.demo.intgr.repositories.car;

import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        Car firstCar = new Car();
        firstCar.setName("first car");
        entityManager.persist(firstCar);

        Car secondCar = new Car();
        secondCar.setName("second car");
        entityManager.persist(secondCar);

        Car thirdCar = new Car();
        thirdCar.setName("third car");
        entityManager.persist(thirdCar);
    }

    @Test
    public void carsIsFound_whenByMatchingName() throws Exception {
        List<Car> expected = carRepository.findCarsByName("first car");
        assertTrue(expected.stream().allMatch(x -> x.getName().equals("first car")));
    }

    @Test
    public void carsIsNotFound_whenByNotMatchingName() throws Exception {
        List<Car> expected = carRepository.findCarsByName("fifth car");
        assertEquals(0, expected.size());
    }

    @Test
    public void carIsNotFound_whenSearchingByPartialNameMatch() throws Exception {
        List<Car> expected = carRepository.findCarsByName("second");
        assertEquals(0, expected.size());
    }
}