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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = HospitalApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PatientFunctionalTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void whenValidInput_thenCreatePatientAndFindHimById() throws Exception {
        JSONObject patient = new JSONObject();
        patient.put("name", "Jacob");
        patient.put("surname", "Baudet");
        patient.put("email", "jbaudet0@zimbio.com");
        patient.put("phone", "6660606234");
        patient.put("pesel", "1233123123213");
        patient.put("city", "Lublin");
        patient.put("country", "Poland");
        patient.put("postalCode", "23-311");
        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(patient.toString()));
        mvc.perform(get("/app-api/patients/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("Jacob")));

    }

    @Test
    public void whenValidInput_thenCreatePatientsAndFindAllOfThem() throws Exception {
        JSONObject patient = new JSONObject();
        patient.put("name", "Jacob");
        patient.put("surname", "Baudet");
        patient.put("email", "jbaudet0@zimbio.com");
        patient.put("phone", "6660606234");
        patient.put("pesel", "1233123123213");
        patient.put("city", "Lublin");
        patient.put("country", "Poland");
        patient.put("postalCode", "23-311");

        JSONObject patient2 = new JSONObject();
        patient2.put("name", "Kamil");
        patient2.put("surname", "Nowak");
        patient2.put("email", "kamil0@zimbio.com");
        patient2.put("phone", "123456789");
        patient2.put("pesel", "12345678900");
        patient2.put("city", "Warszawa");
        patient2.put("country", "Poland");
        patient2.put("postalCode", "23-311");

        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(patient.toString()));
        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(patient2.toString()));
        mvc.perform(get("/app-api/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("Jacob")))
                .andExpect(jsonPath("$[1].name", is("Kamil")));
    }

    @Test
    public void whenValidInput_thenUpdateAllPatientFieldsAndFindHimById() throws Exception {
        JSONObject patient = new JSONObject();
        patient.put("name", "Jacob");
        patient.put("surname", "Baudet");
        patient.put("email", "jbaudet0@zimbio.com");
        patient.put("phone", "6660606234");
        patient.put("pesel", "1233123123213");
        patient.put("city", "Lublin");
        patient.put("country", "Poland");
        patient.put("postalCode", "23-311");

        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(patient.toString()));
        mvc.perform(get("/app-api/patients/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("Jacob")));

        JSONObject patient2 = new JSONObject();
        patient2.put("patientID", 1);
        patient2.put("name", "Kamil");
        patient2.put("surname", "Nowak");
        patient2.put("email", "kamil0@zimbio.com");
        patient2.put("phone", "123456789");
        patient2.put("pesel", "12345678900");
        patient2.put("city", "Warszawa");
        patient2.put("country", "Poland");
        patient2.put("postalCode", "23-311");

        mvc.perform(put("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(patient2.toString()));
        mvc.perform(get("/app-api/patients/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("Kamil")));

    }

    @Test
    public void whenValidInput_thenUpdateOnePatientFieldAndFindHimById() throws Exception {
        JSONObject patient = new JSONObject();
        patient.put("name", "Jacob");
        patient.put("surname", "Baudet");
        patient.put("email", "jbaudet0@zimbio.com");
        patient.put("phone", "6660606234");
        patient.put("pesel", "1233123123213");
        patient.put("city", "Lublin");
        patient.put("country", "Poland");
        patient.put("postalCode", "23-311");

        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(patient.toString()));
        mvc.perform(get("/app-api/patients/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("Jacob")));

        JSONObject patient2 = new JSONObject();
        patient2.put("patientID", 1);
        patient2.put("name", "Kamil");

        mvc.perform(patch("/app-api/patients/1").contentType(MediaType.APPLICATION_JSON).content(patient2.toString()));
        mvc.perform(get("/app-api/patients/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("Kamil")));
    }

    @Test
    public void whenValidInput_thenDeletePatient() throws Exception {
        JSONObject patient = new JSONObject();
        patient.put("name", "Jacob");
        patient.put("surname", "Baudet");
        patient.put("email", "jbaudet0@zimbio.com");
        patient.put("phone", "6660606234");
        patient.put("pesel", "1233123123213");
        patient.put("city", "Lublin");
        patient.put("country", "Poland");
        patient.put("postalCode", "23-311");

        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(patient.toString()));
        mvc.perform(get("/app-api/patients/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("Jacob")));

        mvc.perform(delete("/app-api/patients/1"));
        mvc.perform(get("/app-api/patients").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
        ;
    }

    @Test
    public void givenPatients_whenGetPatients_thenStatus200() throws Exception {
        JSONObject patient = new JSONObject();
        patient.put("name", "Jacob");
        patient.put("surname", "Baudet");
        patient.put("email", "jbaudet0@zimbio.com");
        patient.put("phone", "6660606234");
        patient.put("pesel", "1233123123213");
        patient.put("city", "Lublin");
        patient.put("country", "Poland");
        patient.put("postalCode", "23-311");

        JSONObject patient2 = new JSONObject();
        patient2.put("patientID", 2);
        patient2.put("name", "Kamil");
        patient2.put("surname", "Nowak");
        patient2.put("email", "kamil0@zimbio.com");
        patient2.put("phone", "123456789");
        patient2.put("pesel", "12345678900");
        patient2.put("city", "Warszawa");
        patient2.put("country", "Poland");
        patient2.put("postalCode", "23-311");

        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(patient.toString()));
        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(patient2.toString()));

        mvc.perform(get("/app-api/patients").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("Jacob")))
                .andExpect(jsonPath("$[1].name", is("Kamil")));
    }

    @Test
    public void givenPatients_whenGetPatientById_thenStatus200() throws Exception {
        JSONObject patient = new JSONObject();
        patient.put("name", "Jacob");
        patient.put("surname", "Baudet");
        patient.put("email", "jbaudet0@zimbio.com");
        patient.put("phone", "6660606234");
        patient.put("pesel", "1233123123213");
        patient.put("city", "Lublin");
        patient.put("country", "Poland");
        patient.put("postalCode", "23-311");

        mvc.perform(post("/app-api/patients").contentType(MediaType.APPLICATION_JSON).content(patient.toString()));

        mvc.perform(get("/app-api/patients/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("Jacob")));
    }


}
