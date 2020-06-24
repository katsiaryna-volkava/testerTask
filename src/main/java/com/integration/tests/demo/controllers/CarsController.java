package com.integration.tests.demo.controllers;

import com.integration.tests.demo.dtos.CarDTO;
import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.services.CarService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/cars")
public class CarsController {

    private final CarService carService;

    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/search")
    public List<Car> searchCarByName(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "id", required = false) Long id) {
        return carService.search(name, id);
    }

    @PostMapping("/addCar")
    public void addCar(@RequestBody CarDTO carDTO) {
        carService.addCar(carDTO);
    }

}
