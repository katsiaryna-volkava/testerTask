package com.integration.tests.demo.services;

import com.integration.tests.demo.entities.Car;

import java.util.List;

public interface CarService {

    List<Car> findAllCars();

}
