package com.example.hospital.Functional;

import com.example.hospital.HospitalApplication;
import com.example.hospital.controllers.VisitApiController;
import org.json.JSONObject;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
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
public class VisitFunctionalTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getVisitsThenReturnEmptyArray() throws Exception {
        mvc.perform(get("/app-api/visits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void getVisitByIDThenReturnVisit() throws Exception {
        JSONObject visit = new JSONObject();
        visit.put("visitID", 1);
        visit.put("visitDate", "2022-11-11");
        visit.put("typeVisit", "Boli noga");

        mvc.perform(MockMvcRequestBuilders.post("/app-api/visits/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(visit.toString()));

        mvc.perform(get("/app-api/visits/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.typeVisit", is("Boli noga")));
    }

    @Test
    public void whenValidInput_thenUpdateVisit() throws Exception {
        JSONObject visit = new JSONObject();
        visit.put("visitID", 1);
        visit.put("visitDate", "2022-11-11");
        visit.put("typeVisit", "Boli noga");

        mvc.perform(MockMvcRequestBuilders.post("/app-api/visits/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(visit.toString()));

        mvc.perform(get("/app-api/visits/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.typeVisit", is("Boli noga")))
                .andExpect(jsonPath("$.visitDate", is("2022-11-11")));

        JSONObject visit1 = new JSONObject();
        visit1.put("visitID", 1);
        visit1.put("visitDate", "2022-11-11");
        visit1.put("typeVisit", "Boli noga UPDATE");

        mvc.perform(MockMvcRequestBuilders.patch("/app-api/visits/1/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(visit1.toString()));

        mvc.perform(get("/app-api/visits/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.typeVisit", is("Boli noga UPDATE")))
                .andExpect(jsonPath("$.visitDate", is("2022-11-11")));
    }

    @Test
    public void whenValidInput_thenDeleteVisit() throws Exception {
        JSONObject visit = new JSONObject();
        visit.put("visitID", 1);
        visit.put("visitDate", "2022-11-11");
        visit.put("typeVisit", "Boli noga");

        mvc.perform(MockMvcRequestBuilders.post("/app-api/visits/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(visit.toString()));

        mvc.perform(get("/app-api/visits/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.typeVisit", is("Boli noga")))
                .andExpect(jsonPath("$.visitDate", is("2022-11-11")));

        mvc.perform(MockMvcRequestBuilders.delete("/app-api/visits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(visit.toString()));

        mvc.perform(get("/app-api/visits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }
}
