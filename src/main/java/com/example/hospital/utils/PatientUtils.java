package com.example.hospital.utils;

import com.example.hospital.entities.Patient;

public class PatientUtils {

    public static Patient validatePatient(Patient patchPatient, Patient patient) {
        if(patchPatient.getEmail() != null) {
            patient.setEmail(patchPatient.getEmail());
        }
        if(patchPatient.getName() != null) {
            patient.setName(patchPatient.getName());
        }
        if(patchPatient.getSurname() != null) {
            patient.setSurname(patchPatient.getSurname());
        }
        if(patchPatient.getPhone() != null) {
            patient.setPhone(patchPatient.getPhone());
        }
        if(patchPatient.getPesel() != null) {
            patient.setPesel(patchPatient.getPesel());
        }
        if(patchPatient.getCity() != null) {
            patient.setCity(patchPatient.getCity());
        }
        if(patchPatient.getCountry() != null) {
            patient.setCountry(patchPatient.getCountry());
        }
        if(patchPatient.getPostalCode() != null) {
            patient.setPostalCode(patchPatient.getPostalCode());
        }
        return patient;
    }
}
