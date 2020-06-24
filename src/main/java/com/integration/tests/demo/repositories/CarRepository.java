package com.integration.tests.demo.repositories;

import com.integration.tests.demo.entities.Car;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findCarsByName(String name);
}
