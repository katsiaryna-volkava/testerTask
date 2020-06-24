package com.integration.tests.demo.services;

import com.integration.tests.demo.dtos.CarDTO;
import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.repositories.CarRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void addCar(CarDTO carDTO) {
        Car car = new Car();
        car.setName(carDTO.getName());
        carRepository.save(car);
    }

    @Override
    public List<Car> search(String name, Long id) {
        List<Car> result;
        if (id != null) {
            result = new ArrayList<>();
            carRepository.findById(id).ifPresent(result::add);
        } else if (!StringUtils.isEmpty(name)) {
            result = carRepository.findCarsByName(name);
        } else {
            result = carRepository.findAll();
        }
        return result;
    }

}
