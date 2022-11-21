package com.example.hospital.Functional;

import com.example.hospital.HospitalApplication;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = HospitalApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DoctorFunctionalTest {
    @Autowired
    private MockMvc mvc;

    JSONObject doctor1 = new JSONObject();
    JSONObject doctor2 = new JSONObject();
    JSONObject doctorUpdated = new JSONObject();

    public void initDoctor1(JSONObject doctor) throws Exception{
        doctor.put("name", "Jacobo");
        doctor.put("doctorID", 1);
        doctor.put("surname", "Coco");
        doctor.put("email", "coco@gmail.com");
        doctor.put("phone", "602657001");
        doctor.put("speciality", "Doctor");
    }

    public void initDoctor2(JSONObject doctor) throws Exception{
        doctor.put("name", "Paul");
        doctor.put("doctorID", 2);
        doctor.put("surname", "Simon");
        doctor.put("email", "simon@gmail.com");
        doctor.put("phone", "601657001");
        doctor.put("speciality", "Doctor");
    }

    public void update(JSONObject doctor) throws Exception{
        doctor.put("name", "Andrei");
        doctor.put("doctorID", 1);
        doctor.put("surname", "Messi");
        doctor.put("email", "messi@gmail.com");
        doctor.put("phone", "607657001");
        doctor.put("speciality", "Gynaecologist");
    }


    @Test
    public void whenValidInput_thenCreateDoctorAndReturnDoctor() throws IOException, Exception {
        initDoctor1(doctor1);
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(doctor1.toString()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(doctor1.get("name"))))
                .andExpect(jsonPath("$.surname", is(doctor1.get("surname"))));
    }

    @Test
    public void whenValidInput_thenUpdateDoctorAndReturnDoctor() throws IOException, Exception {
        initDoctor1(doctor1);
        update(doctorUpdated);

        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(doctor1.toString()));
        mvc.perform(patch("/app-api/doctors/{id}", doctor1.get("doctorID")).contentType(MediaType.APPLICATION_JSON).content(doctor1.toString()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(doctor1.get("name"))))
                .andExpect(jsonPath("$.surname", is(doctor1.get("surname"))));
    }

    @Test
    public void whenValidInput_thenDeleteDoctor() throws IOException, Exception {
        initDoctor1(doctor1);
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(doctor1.toString()));
        mvc.perform(delete("/app-api/doctors/{id}", doctor1.get("doctorID"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenDoctors_whenGetDoctors_thenStatus200() throws Exception {
        initDoctor1(doctor1);
        initDoctor2(doctor2);
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(doctor1.toString()));
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(doctor2.toString()));
        mvc.perform(get("/app-api/doctors").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("Jacobo")))
                .andExpect(jsonPath("$[1].name", is("Paul")));
    }

    @Test
    public void givenDoctor_whenGetDoctorById_thenStatus200() throws Exception {
        initDoctor1(doctor1);
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(doctor1.toString()));
        mvc.perform(get("/app-api/doctors/{id}", doctor1.get("doctorID"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Jacobo")));
    }

    @Test
    public void whenValidInput_thenUpdateAllDoctorFieldsAndReturnDoctor() throws Exception {
        initDoctor1(doctor1);
        update(doctorUpdated);
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(doctor1.toString()));
        mvc.perform(put("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(doctorUpdated.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Andrei")));
    }
}