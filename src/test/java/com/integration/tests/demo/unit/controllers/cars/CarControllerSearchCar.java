package com.integration.tests.demo.unit.controllers.cars;

import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.repositories.CarRepository;
import com.integration.tests.demo.services.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarControllerSearchCar {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @MockBean
    private CarServiceImpl carService;

    private List<Car> carsList;

    @Test
    public void shouldFetchAllCars_whenWithoutParams() throws Exception {

        // assuming that expected result is that
        // when there are no parameters rendered - all entries should be returned

        given(carService.search(null, null)).willReturn(carsList);

        this.mockMvc.perform(get("/search"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    public void shouldFetchData_byExistingName() throws Exception {

        given(carService.search("test", null)).willReturn(carsList);

        this.mockMvc.perform(get("/search")
                .param("name", "test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].name").value("test"))
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    public void shouldFetchData_byExistingId() throws Exception {

        Car car = new Car();
        car.setName("test");
        car.setId(1L);
        carRepository.save(car);

        Car car2 = new Car();
        car2.setName("test 2");
        car2.setId(2L);
        carRepository.save(car2);

        this.mockMvc.perform(get("/search")
                .param("id", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("test 2"));
    }

    @Test
    public void shouldAnswer404_whenByNameThatDoesNotExist() throws Exception {

        // implying that expected result is that
        // when searching by name that does not exist - a not found error will be returned

        Car car = new Car();
        car.setName("test");
        car.setId(1L);
        carRepository.save(car);

        Car car2 = new Car();
        car2.setName("test2");
        car2.setId(2L);
        carRepository.save(car2);

        this.mockMvc.perform(get("/search")
                .param("name", "test name does not exist"))
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(0)))
                .andExpect(status().is(404));
    }

    @Test
    public void shouldAnswer404_whenByIdThatDoesNotExist() throws Exception {

        // implying that expected result is that
        // when searching by Id that does not exist - a not found error will be returned

        Car car = new Car();
        car.setName("test");
        car.setId(1L);
        carRepository.save(car);

        Car car2 = new Car();
        car2.setName("test2");
        car2.setId(2L);
        carRepository.save(car2);

        this.mockMvc.perform(get("/search")
                .param("id", "144"))
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(0)))
                .andExpect(status().is(404));
    }

    @Test
    public void shouldFetchData_whenAllParametersRenderedAndValid() throws Exception {
        Car car = new Car();
        car.setName("test");
        car.setId(1L);
        carRepository.save(car);

        Car car2 = new Car();
        car2.setName("test2");
        car2.setId(2L);
        carRepository.save(car2);

        this.mockMvc.perform(get("/search")
                .param("id", "1")
                .param("name", "test"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].name").value("test"))
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    public void shouldAnswer404_whenAllParametersRenderedAndBothNotValid() throws Exception {
        Car car = new Car();
        car.setName("test");
        car.setId(1L);
        carRepository.save(car);

        Car car2 = new Car();
        car2.setName("test2");
        car2.setId(2L);
        carRepository.save(car2);

        this.mockMvc.perform(get("/search")
                .param("id", "132")
                .param("name", "test name that don't exist"))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.size()", is(0)));
    }

    @Test
    public void shouldAnswer404_whenAllParametersRenderedAndOneNotValid() throws Exception {

        //assuming that expected result is that car is returned if all rendered parameters are satisfied

        Car car = new Car();
        car.setName("test");
        car.setId(1L);
        carRepository.save(car);

        Car car2 = new Car();
        car2.setName("test2");
        car2.setId(2L);
        carRepository.save(car2);

        this.mockMvc.perform(get("/search")
                .param("id", "1")
                .param("name", "test name that don't exist"))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.size()", is(0)));
    }

}
