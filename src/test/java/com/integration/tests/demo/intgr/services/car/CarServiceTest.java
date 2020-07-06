package com.integration.tests.demo.intgr.services.car;

import com.integration.tests.demo.dtos.CarDTO;
import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.repositories.CarRepository;
import com.integration.tests.demo.services.CarServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarServiceImpl carService;

    @BeforeEach
    public void setUpTestData() {
        Car car1 = new Car();
        car1.setName("test car 1");

        Car car2 = new Car();
        car2.setName("test car 2");

        Car car3 = new Car();
        car3.setName("test car 3");

        Car car4 = new Car();
        car4.setName("test car");

        Car car5 = new Car();
        car5.setName("test car");

        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        carRepository.save(car4);
        carRepository.save(car5);
    }

    @Test
    public void searchCar_allCarsCanBeFetched_whenWithNoParameters() throws Exception {
        List<Car> expected = carService.search("", null);
        assertEquals(5, expected.size());
    }

    @Test
    public void searchCar_allMatchingCarsCanBeFetched_whenSearchByName() throws Exception {
        List<Car> expected = carService.search("test car", null);
        assertEquals(2, expected.size());
    }

    @Test
    public void searchCar_oneCarIsFetched_whenSearchById() throws Exception {
        List<Car> expected = carService.search("test car 2", null);
        assertEquals(1, expected.size());
    }

    @Test
    public void searchCar_shouldReturnEmptyArray_whenThereAreNoCars() throws Exception {
        carRepository.deleteAll();
        List<Car> expected = carService.search("", null);
        assertEquals(0, expected.size());
    }

    @Test
    public void searchCar_shouldReturnEmptyArray_whenThereAreNoMatchingCars() throws Exception {
        List<Car> expected = carService.search("car not exists", null);
        assertEquals(0, expected.size());
    }

    @Test
    public void shouldAddCar() {
        CarDTO car = new CarDTO();
        car.setName("test");

        carService.addCar(car);
        int repoSizeAfterAdding = carRepository.findAll().size();

        assertEquals(6, repoSizeAfterAdding);
    }

    @AfterEach
    public void cleanTestData() {
        carRepository.deleteAll();
    }
}
