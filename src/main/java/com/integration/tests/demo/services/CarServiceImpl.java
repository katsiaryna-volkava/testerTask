package com.integration.tests.demo.services;

import com.integration.tests.demo.dtos.CarDTO;
import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    @Override
    public void addCar(CarDTO carDTO) {
        Car car = new Car();
        car.setName(carDTO.getName());
        carRepository.save(car);
    }
}
