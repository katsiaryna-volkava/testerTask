package com.integration.tests.demo.intgr.controllers.car;

import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CarsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarRepository carRepository;

    private List<Car> carsListWith2Cars;
    private List<Car> carsListWith1Car;

    @BeforeEach
    void setupTestData() {
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
    }

    @Test
    public void searchCars_shouldFetchCars() throws Exception {
        given(carRepository.findAll()).willReturn(carsListWith2Cars);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/search"))
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    public void searchCars_shouldFetchAllCarsWithCorrectInfo() throws Exception {
        Car newCar = new Car();
        newCar.setId(2L);
        newCar.setName("test 2");

        given(carRepository.findCarsByName("test 2")).willReturn(carsListWith2Cars);
        given(carRepository.findById(2L)).willReturn(Optional.of(newCar));

        this.mockMvc.perform(get("/search")
                .param("id", "2")
                .param("name", "test 2"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].name").value("test 2"))
                .andExpect(jsonPath("$[0].id").value("2"));
    }

    @Test
    public void shouldFetchData_byExistingName() throws Exception {
        given(carRepository.findCarsByName("test")).willReturn(carsListWith1Car);

        this.mockMvc.perform(get("/search")
                .param("name", "test"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].name").value("test"));
    }

    @Test
    public void shouldFetchData_byExistingId() throws Exception {
        Car newCar = new Car();
        newCar.setId(2L);
        newCar.setName("test 2");

        given(carRepository.findById(2L)).willReturn(Optional.of(newCar));

        this.mockMvc.perform(get("/search")
                .param("id", "2"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id").value("2"));
    }

    @Test
    public void shouldAnswer404_whenByNameThatDoesNotExist() throws Exception {
    }

    @Test
    public void shouldAnswer404_whenByIdThatDoesNotExist() throws Exception {
    }

    @Test
    public void shouldAnswer404_whenAllParametersRenderedAndBothDoNotValid() throws Exception {
    }

    @Test
    public void shouldAnswer404_whenAllParametersRenderedAndOneNotValid() throws Exception {
    }

    @Test
    public void shouldAnswer400_whenAllParametersAreMissing() throws Exception {
    }

    @Test
    public void shouldAnswer400_whenIdParameterIsMissing() throws Exception {
    }

    @Test
    public void shouldAnswer400_whenNameParameterIsMissing() throws Exception {
    }

    @Test
    public void searchCars_should405WithPost() throws Exception {

        given(carRepository.findAll()).willReturn(carsListWith2Cars);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/search"))
                .andDo(print())
                .andExpect(status().is(405));
    }

    @Test
    public void searchCars_should405WithPut() throws Exception {

        given(carRepository.findAll()).willReturn(carsListWith2Cars);

        this.mockMvc.perform(put("/search"))
                .andDo(print())
                .andExpect(status().is(405));
    }

    @Test
    public void searchCars_should405WithDelete() throws Exception {

        given(carRepository.findAll()).willReturn(carsListWith2Cars);

        this.mockMvc.perform(put("/search"))
                .andDo(print())
                .andExpect(status().is(405));
    }

    @Test
    public void shouldAddCarWithName() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/addCar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"car1\"}"))
                .andDo(print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void shouldReturn400_whenAddCarWithoutContent() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/addCar")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void shouldReturn400_whenAddCarWithEmptyName() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/addCar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andDo(print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void addCar_should405WithPut() throws Exception {
        this.mockMvc.perform(put("/addCar")
                .content("test car"))
                .andDo(print())
                .andExpect(status().is(405));
    }

    @Test
    public void addCar_should405WithGet() throws Exception {
        this.mockMvc.perform(get("/addCar"))
                .andDo(print())
                .andExpect(status().is(405));
    }

    @Test
    public void addCar_should405WithDelete() throws Exception {
        this.mockMvc.perform(delete("/addCar")
                .content("test car"))
                .andDo(print())
                .andExpect(status().is(405));
    }
}