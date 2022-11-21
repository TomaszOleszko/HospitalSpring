package com.example.hospital.Integration;

import com.example.hospital.HospitalApplication;
import com.example.hospital.dao.DoctorRepository;
import com.example.hospital.entities.Doctor;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
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
public class DoctorIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private DoctorRepository doctorRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void whenValidInput_thenCreateDoctorAndFindHimById() throws IOException, Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Doctor doctor = new Doctor();
        doctor.setDoctorID(1L);
        doctor.setName("Jacobo");
        doctor.setEmail("jbaudet0@zimbio.com");
        doctor.setSurname("Baudet");
        doctor.setPhone("6660606234");
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(doctor)));
        Optional<Doctor> found = doctorRepository.findById(1L);
        assertEquals(found.get().getName(), "Jacobo");

    }

    @Test
    public void whenValidInput_thenUpdateDoctorAndFindHimById() throws IOException, Exception {
        Doctor doctor = new Doctor();
        doctor.setDoctorID(1L);
        doctor.setName("Jacobo");
        doctor.setEmail("jbaudet0@zimbio.com");
        doctor.setSurname("Baudet");
        doctor.setPhone("6660606234");
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(doctor)));
        doctor.setName("Omar");
        mvc.perform(patch("/app-api/doctors/{id}", doctor.getDoctorID()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(doctor)));
        Optional<Doctor> found = doctorRepository.findById(1L);
        assertEquals(found.get().getName(), "Omar");
    }

    @Test
    public void whenValidInput_thenDeleteDoctorAndReturnNull() throws IOException, Exception {
        Doctor doctor = new Doctor();
        doctor.setDoctorID(1L);
        doctor.setName("Jacobo");
        doctor.setEmail("jbaudet0@zimbio.com");
        doctor.setSurname("Baudet");
        doctor.setPhone("6660606234");
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(doctor)));
        Optional<Doctor> found = doctorRepository.findById(1L);
        mvc.perform(delete("/app-api/doctors/{id}", found.get().getDoctorID()));
        Optional<Doctor> found2 = doctorRepository.findById(1L);
        assertTrue(found2.isEmpty());
    }

    @Test
    public void givenDoctors_whenGetDoctors_thenStatus200() throws Exception {
        Doctor doctor1 = new Doctor();
        Doctor doctor2 = new Doctor();
        doctor1.setDoctorID(1L);
        doctor1.setName("Jacobo");
        doctor1.setEmail("jbaudet0@zimbio.com");
        doctor1.setSurname("Baudet");
        doctor1.setPhone("6660606234");
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(doctor1)));
        doctor2.setDoctorID(2L);
        doctor2.setName("Andrei");
        doctor2.setEmail("andrei0@zimbio.com");
        doctor2.setSurname("Coco");
        doctor2.setPhone("6670606234");
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(doctor2)));
        mvc.perform(get("/app-api/doctors").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2)))).andExpect(jsonPath("$[0].name", is("Jacobo"))).andExpect(jsonPath("$[1].name", is("Andrei")));
    }

    @Test
    public void givenDoctor_whenGetDoctorById_thenStatus200() throws Exception {
        Doctor doctor1 = new Doctor();
        doctor1.setDoctorID(1L);
        doctor1.setName("Jacobo");
        doctor1.setEmail("jbaudet0@zimbio.com");
        doctor1.setSurname("Baudet");
        doctor1.setPhone("6660606234");
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(doctor1)));
        mvc.perform(get("/app-api/doctors/{id}", doctor1.getDoctorID()).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(jsonPath("name", is("Jacobo")));
    }

    @Test
    public void whenValidInput_thenUpdateAllDoctorFieldsAndFindHimById() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setDoctorID(1L);
        doctor.setName("Jacob");
        doctor.setEmail("jbaudet0@zimbio.com");
        doctor.setSurname("Baudet");
        doctor.setPhone("6660606234");
        mvc.perform(post("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(doctor)));
        doctor.setName("Andrei");
        doctor.setEmail("andrei@zimbio.com");
        doctor.setSurname("Baudet2");
        doctor.setPhone("6660606236");
        mvc.perform(put("/app-api/doctors").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(doctor)));
        Optional<Doctor> found = doctorRepository.findById((long) 1);
        assertEquals(found.get().getName(), "Andrei");
    }
}
