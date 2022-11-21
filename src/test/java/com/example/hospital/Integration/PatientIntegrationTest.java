package com.example.hospital.Integration;

import com.example.hospital.HospitalApplication;
import com.example.hospital.dao.PatientRepository;
import com.example.hospital.entities.Patient;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = HospitalApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PatientIntegrationTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PatientRepository repository;

    @Test
    public void whenValidInput_thenCreatePatientAndFindHimById() throws Exception {
        Patient jacob = new Patient();
        jacob.setPatientID(1L);
        jacob.setName("Jacob");
        jacob.setEmail("jbaudet0@zimbio.com");
        jacob.setSurname("Baudet");
        jacob.setPesel("1233123123213");
        jacob.setPhone("6660606234");
        jacob.setCountry("Poland");
        jacob.setPostalCode("23-311");
        jacob.setCity("Lublin");
        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(jacob)));
        Optional<Patient> found = repository.findById((long) 1);
        assertEquals(found.get().getName(), "Jacob");
    }

    @Test
    public void whenValidInput_thenCreatePatientsAndFindAllOfThem() throws Exception {
        Patient jacob = new Patient();
        jacob.setPatientID(1L);
        jacob.setName("Jacob");
        jacob.setEmail("jbaudet0@zimbio.com");
        jacob.setSurname("Baudet");
        jacob.setPesel("1233123123213");
        jacob.setPhone("6660606234");
        jacob.setCountry("Poland");
        jacob.setPostalCode("23-311");
        jacob.setCity("Lublin");

        Patient kamil = new Patient();
        kamil.setPatientID(2L);
        kamil.setName("Kamil");
        kamil.setEmail("kamil0@zimbio.com");
        kamil.setSurname("Nowak");
        kamil.setPesel("12345678900");
        kamil.setPhone("123456789");
        kamil.setCountry("Poland");
        kamil.setPostalCode("23-311");
        kamil.setCity("Warszawa");

        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(jacob)));
        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(kamil)));

        List<Patient> found = repository.findAll();
        assertThat(found).extracting(Patient::getName).containsAll(List.of("Jacob", "Kamil"));
    }

    @Test
    public void whenValidInput_thenUpdateAllPatientFieldsAndFindHimById() throws Exception {
        Patient jacob = new Patient();
        jacob.setPatientID(1L);
        jacob.setName("Jacob");
        jacob.setEmail("jbaudet0@zimbio.com");
        jacob.setSurname("Baudet");
        jacob.setPesel("1233123123213");
        jacob.setPhone("6660606234");
        jacob.setCountry("Poland");
        jacob.setPostalCode("23-311");
        jacob.setCity("Lublin");
        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(jacob)));
        Optional<Patient> found = repository.findById((long) 1);
        assertEquals(found.get().getName(), "Jacob");

        jacob.setName("Kamil");
        jacob.setEmail("test@zimbio.com");
        jacob.setSurname("Baudet2");
        jacob.setPesel("1233123122321");
        jacob.setPhone("6660606236");
        jacob.setCountry("Poland2");
        jacob.setPostalCode("23-312");
        jacob.setCity("Lublin2");
        mvc.perform(put("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(jacob)));
        Optional<Patient> found2 = repository.findById((long) 1);
        assertEquals(found2.get().getName(), "Kamil");
    }

    @Test
    public void whenValidInput_thenUpdateOnePatientFieldAndFindHimById() throws Exception {
        Patient jacob = new Patient();
        jacob.setPatientID(1L);
        jacob.setName("Jacob");
        jacob.setEmail("jbaudet0@zimbio.com");
        jacob.setSurname("Baudet");
        jacob.setPesel("1233123123213");
        jacob.setPhone("6660606234");
        jacob.setCountry("Poland");
        jacob.setPostalCode("23-311");
        jacob.setCity("Lublin");
        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(jacob)));
        Optional<Patient> found = repository.findById((long) 1);
        assertEquals(found.get().getName(), "Jacob");

        jacob.setName("Kamil");
        mvc.perform(patch("/app-api/patients/" + found.get().getPatientID()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(jacob)));
        Optional<Patient> found2 = repository.findById((long) 1);
        assertEquals(found2.get().getName(), "Kamil");
    }

    @Test
    public void whenValidInput_thenDeletePatient() throws Exception {
        Patient jacob = new Patient();
        jacob.setPatientID(1L);
        jacob.setName("Jacob");
        jacob.setEmail("jbaudet0@zimbio.com");
        jacob.setSurname("Baudet");
        jacob.setPesel("1233123123213");
        jacob.setPhone("6660606234");
        jacob.setCountry("Poland");
        jacob.setPostalCode("23-311");
        jacob.setCity("Lublin");
        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(jacob)));
        Optional<Patient> found = repository.findById((long) 1);
        assertEquals(found.get().getName(), "Jacob");

        mvc.perform(delete("/app-api/patients/" + found.get().getPatientID()));
        Optional<Patient> found2 = repository.findById((long) 1);
        assertTrue(found2.isEmpty());
    }

    @Test
    public void givenPatients_whenGetPatients_thenStatus200() throws Exception {
        Patient jacob = new Patient();
        jacob.setPatientID(1L);
        jacob.setName("Jacob");
        jacob.setEmail("jbaudet0@zimbio.com");
        jacob.setSurname("Baudet");
        jacob.setPesel("1233123123213");
        jacob.setPhone("6660606234");
        jacob.setCountry("Poland");
        jacob.setPostalCode("23-311");
        jacob.setCity("Lublin");

        Patient kamil = new Patient();
        kamil.setPatientID(2L);
        kamil.setName("Kamil");
        kamil.setEmail("kamil0@zimbio.com");
        kamil.setSurname("Nowak");
        kamil.setPesel("12345678900");
        kamil.setPhone("123456789");
        kamil.setCountry("Poland");
        kamil.setPostalCode("23-311");
        kamil.setCity("Warszawa");

        repository.save(jacob);
        repository.save(kamil);

        mvc.perform(get("/app-api/patients").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2)))).andExpect(jsonPath("$[0].name", is("Jacob"))).andExpect(jsonPath("$[1].name", is("Kamil")));
    }

    @Test
    public void givenPatients_whenGetPatientById_thenStatus200() throws Exception {
        Patient jacob = new Patient();
        jacob.setPatientID(1L);
        jacob.setName("Jacob");
        jacob.setEmail("jbaudet0@zimbio.com");
        jacob.setSurname("Baudet");
        jacob.setPesel("1233123123213");
        jacob.setPhone("6660606234");
        jacob.setCountry("Poland");
        jacob.setPostalCode("23-311");
        jacob.setCity("Lublin");

        repository.save(jacob);

        mvc.perform(get("/app-api/patients/1").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(jsonPath("name", is("Jacob")));
    }


}
