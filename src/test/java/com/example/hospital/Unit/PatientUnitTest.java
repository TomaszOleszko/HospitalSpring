package com.example.hospital.Unit;

import com.example.hospital.entities.Patient;
import com.example.hospital.utils.PatientUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientUnitTest {
    Patient jacob = new Patient();

    @BeforeEach
    void init() {
        jacob.setPatientID(1L);
        jacob.setName("Jacob");
        jacob.setEmail("jbaudet0@zimbio.com");
        jacob.setSurname("Baudet");
        jacob.setPesel("1233123123213");
        jacob.setPhone("6660606234");
        jacob.setCountry("Poland");
        jacob.setPostalCode("23-311");
        jacob.setCity("Lublin");
    }

    @Test
    void givenNewPatientData_whenValidatePatient_toReturnNewPatient() {
        Patient newPatientData = new Patient();
        newPatientData.setPatientID(1L);
        newPatientData.setName("Michał");
        newPatientData.setEmail("michał@zimbio.com");
        newPatientData.setSurname("Kowalski");
        newPatientData.setPesel("00000000000");
        newPatientData.setPhone("123456789");
        newPatientData.setCountry("Germany");
        newPatientData.setPostalCode("00-000");
        newPatientData.setCity("Munchen");

        PatientUtils.validatePatient(newPatientData, jacob);

        assertAll("Transaction test", () -> assertEquals("Michał", jacob.getName()), () -> assertEquals("michał@zimbio.com", jacob.getEmail()), () -> assertEquals("Kowalski", jacob.getSurname()), () -> assertEquals("00000000000", jacob.getPesel()), () -> assertEquals("123456789", jacob.getPhone()), () -> assertEquals("Germany", jacob.getCountry()), () -> assertEquals("00-000", jacob.getPostalCode()), () -> assertEquals("Munchen", jacob.getCity()));
    }

}
