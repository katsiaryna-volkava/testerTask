package com.integration.tests.demo.services;

import com.integration.tests.demo.dtos.CarDTO;
import com.integration.tests.demo.entities.Car;
import java.util.List;

public interface CarService {

    void addCar(CarDTO carDTO);

    List<Car> search(String name, Long id);
}
