package com.integration.tests.demo.intgr.services.car;

import com.integration.tests.demo.dtos.CarDTO;
import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.repositories.CarRepository;
import com.integration.tests.demo.services.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    private List<Car> carsListWith2Cars;
    private List<Car> carsListWith1Car;

    @BeforeEach
    public void setUpTestData() {

        Car car = new Car();
        car.setName("test");
        car.setId(1L);

        Car car2 = new Car();
        car2.setName("test 2");
        car2.setId(2L);

        this.carsListWith2Cars = new ArrayList<>();
        this.carsListWith1Car = new ArrayList<>();
        this.carsListWith2Cars.add(car);
        this.carsListWith2Cars.add(car2);
        this.carsListWith1Car.add(car);

       /* Car car1 = new Car();
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
        carRepository.save(car5);*/
    }

    @Test
    public void searchCar_allCarsCanBeFetched_whenWithNoParameters() throws Exception {
        given(carRepository.findAll()).willReturn(carsListWith2Cars);

        List<Car> expected = carService.search("", null);
        assertEquals(2, expected.size());
    }

    @Test
    public void searchCar_allMatchingCarsCanBeFetched_whenSearchByName() throws Exception {
        given(carRepository.findCarsByName("test")).willReturn(carsListWith1Car);

        List<Car> expected = carService.search("test", null);
        assertEquals(1, expected.size());
    }

    @Test
    public void searchCar_oneCarIsFetched_whenSearchById() throws Exception {
        Car car = new Car();
        car.setId(5L);

        given(carRepository.findById(5L)).willReturn(java.util.Optional.of(car));

        List<Car> expected = carService.search("", 5L);
        assertEquals(1, expected.size());
    }

    @Test
    public void searchCar_shouldReturnEmptyArray_whenThereAreNoCars() throws Exception {
        given(carRepository.findAll()).willReturn(Collections.emptyList());

        List<Car> expected = carService.search("", null);
        assertEquals(0, expected.size());
    }

    @Test
    public void searchCar_shouldReturnEmptyArray_whenThereAreNoMatchingCars() throws Exception {
        given(carRepository.findAll()).willReturn(carsListWith2Cars);

        List<Car> expected = carService.search("car not exists", 133L);
        assertEquals(0, expected.size());
    }

    @Test
    public void shouldAddCar() {
        given(carRepository.findAll()).willReturn(carsListWith2Cars);

        CarDTO car = new CarDTO();
        car.setName("test car addition");

        carService.addCar(car);
        int repoSizeAfterAdding = carRepository.findAll().size();

        assertEquals(3, repoSizeAfterAdding);
    }
}
