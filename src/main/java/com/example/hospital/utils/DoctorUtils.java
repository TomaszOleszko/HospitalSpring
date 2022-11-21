package com.example.hospital.utils;

import com.example.hospital.entities.Doctor;

public class DoctorUtils {

    public static Doctor validateDoctor(Doctor patchDoctor, Doctor doctor) {
        if (patchDoctor.getEmail() != null) {
            doctor.setEmail(patchDoctor.getEmail());
        }
        if (patchDoctor.getName() != null) {
            doctor.setName(patchDoctor.getName());
        }
        if (patchDoctor.getSurname() != null) {
            doctor.setSurname(patchDoctor.getSurname());
        }
        if (patchDoctor.getPhone() != null) {
            doctor.setPhone(patchDoctor.getPhone());
        }
        if (patchDoctor.getSpeciality() != null) {
            doctor.setSpeciality(patchDoctor.getSpeciality());
        }
        return doctor;
    }
}
