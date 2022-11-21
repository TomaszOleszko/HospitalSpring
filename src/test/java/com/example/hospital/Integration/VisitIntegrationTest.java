package com.example.hospital.Integration;


import com.example.hospital.HospitalApplication;
import com.example.hospital.controllers.VisitApiController;
import com.example.hospital.dao.DoctorRepository;
import com.example.hospital.dao.PatientRepository;
import com.example.hospital.dao.VisitRepository;
import com.example.hospital.entities.Doctor;
import com.example.hospital.entities.Patient;
import com.example.hospital.entities.Visit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = HospitalApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
@Import(VisitApiController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VisitIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Test
    public void whenValidInput_theGetVisit() throws Exception {
        Visit visit = new Visit();
        visit.setVisitID(1L);
        visit.setTypeVisit("Typ");
        createVisit(visit);

        Optional<Visit> before = visitRepository.findById(1L);
        assertFalse(before.isEmpty());

        mvc.perform(get("/app-api/visits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].typeVisit", is("Typ")));
    }

    @Test
    public void whenValidInput_theGetVisitByID() throws Exception {
        Visit visit = new Visit();
        visit.setVisitID(1L);
        visit.setTypeVisit("Typ");
        createVisit(visit);

        Optional<Visit> before = visitRepository.findById(1L);
        assertFalse(before.isEmpty());

        mvc.perform(get("/app-api/visits/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.typeVisit", is("Typ")));
    }

    @Test
    public void whenValidInput_thenCreateVisit() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Visit visit = new Visit();
        visit.setVisitID(0L);
        visit.setTypeVisit("Typ");

        Doctor jacobo = new Doctor();
        jacobo.setDoctorID(1L);
        jacobo.setName("JacoboDoctor");
        jacobo.setEmail("jbaudet0@zimbio.com");
        jacobo.setSurname("Baudet");
        jacobo.setPhone("6660606234");
        createDoctor(jacobo);

        visit.setDoctor(jacobo);

        Patient jacobo1 = new Patient();
        jacobo1.setPatientID(1L);
        jacobo1.setName("Jacobo");
        jacobo1.setEmail("jbaudet0@zimbio.com");
        jacobo1.setSurname("Baudet");
        jacobo1.setPesel("1233123123213");
        jacobo1.setPhone("6660606234");
        jacobo1.setCountry("Poland");
        jacobo1.setPostalCode("23-311");
        jacobo1.setCity("Lublin");
        createPatient(jacobo1);

        visit.setPatient(jacobo1);

        mvc.perform(MockMvcRequestBuilders.post("/app-api/visits/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(visit)));


        List<Visit> found = visitRepository.findAll();

        assertThat(found).extracting(Visit::getTypeVisit)
                .containsOnly("Typ");
        assertEquals(found.get(0).getDoctor().getName(), "JacoboDoctor");
        assertEquals(found.get(0).getPatient().getName(), "Jacobo");
    }

    @Test
    public void whenValidInput_thenUpdateVisit() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Visit visit = new Visit();
        visit.setVisitID(0L);
        visit.setTypeVisit("Typ");

        Doctor jacobo = new Doctor();
        jacobo.setDoctorID(1L);
        jacobo.setName("JacoboDoctor");
        jacobo.setEmail("jbaudet0@zimbio.com");
        jacobo.setSurname("Baudet");
        jacobo.setPhone("6660606234");
        createDoctor(jacobo);

        visit.setDoctor(jacobo);

        Patient jacobo1 = new Patient();
        jacobo1.setPatientID(1L);
        jacobo1.setName("Jacobo");
        jacobo1.setEmail("jbaudet0@zimbio.com");
        jacobo1.setSurname("Baudet");
        jacobo1.setPesel("1233123123213");
        jacobo1.setPhone("6660606234");
        jacobo1.setCountry("Poland");
        jacobo1.setPostalCode("23-311");
        jacobo1.setCity("Lublin");
        createPatient(jacobo1);

        visit.setPatient(jacobo1);

        createVisit(visit);

        List<Visit> before = visitRepository.findAll();
        assertEquals(before.get(0).getDoctor().getName(), "JacoboDoctor");
        assertEquals(before.get(0).getTypeVisit(), "Typ");


        visit.setTypeVisit("NOWY TYP");
        mvc.perform(MockMvcRequestBuilders.patch("/app-api/visits/1/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(visit)));

        List<Visit> found = visitRepository.findAll();

        assertThat(found).extracting(Visit::getTypeVisit)
                .containsOnly("NOWY TYP");
        assertEquals(found.get(0).getDoctor().getName(), "JacoboDoctor");
        assertEquals(found.get(0).getPatient().getName(), "Jacobo");
    }

    @Test
    public void whenValidInput_thenDeleteVisit() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Visit visit = new Visit();
        visit.setVisitID(1L);
        visit.setTypeVisit("Typ");
        createVisit(visit);

        Optional<Visit> before = visitRepository.findById(1L);
        assertFalse(before.isEmpty());

        mvc.perform(MockMvcRequestBuilders.delete("/app-api/visits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(visit)));

        Optional<Visit> found = visitRepository.findById(1L);
        assertTrue(found.isEmpty());
    }

    private void createVisit(Visit p) {
        visitRepository.save(p);
    }

    private void createPatient(Patient p) {
        patientRepository.save(p);
    }

    private void createDoctor(Doctor p) {
        doctorRepository.save(p);
    }
}
