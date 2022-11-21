package com.example.hospital.Unit;

import com.example.hospital.entities.Doctor;
import com.example.hospital.utils.DoctorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorUnitTest {

    Doctor doctor = new Doctor();

    @BeforeEach
    void init() {
        doctor.setDoctorID(1L);
        doctor.setName("Jacobo");
        doctor.setEmail("jbaudet0@zimbio.com");
        doctor.setSurname("Baudet");
        doctor.setPhone("6660606234");
    }

    @Test
    void giveNewDoctor_whenValidateDoctor_toReturnNewDoctor(){
        Doctor newDoctor = new Doctor();
        newDoctor.setDoctorID(1L);
        newDoctor.setName("Michał");
        newDoctor.setEmail("michał@zimbio.com");
        newDoctor.setSurname("Kowalski");
        newDoctor.setPhone("123456789");

        DoctorUtils.validateDoctor(newDoctor, doctor);

        assertAll("Transaction test",
                () -> assertEquals("Michał", doctor.getName()),
                () -> assertEquals("michał@zimbio.com", doctor.getEmail()),
                () -> assertEquals("Kowalski", doctor.getSurname()),
                () -> assertEquals("123456789", doctor.getPhone()));
    }
}

