package com.integration.tests.demo.controllers;

import com.integration.tests.demo.dtos.CarDTO;
import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.services.CarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/cars")
public class CarsController {

    private final CarService carService;

    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/findAllCars")
    public List<Car> findAllCars() {
        return carService.findAllCars();
    }

    @PostMapping("/addCar")
    public void addCar(@RequestBody CarDTO carDTO) {
        carService.addCar(carDTO);
    }

}
