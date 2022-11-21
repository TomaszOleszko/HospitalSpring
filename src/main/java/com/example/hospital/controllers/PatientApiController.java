package com.example.hospital.controllers;

import com.example.hospital.entities.Patient;
import com.example.hospital.services.PatientService;
import com.example.hospital.utils.PatientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app-api/patients")
public class PatientApiController {
    @Autowired
    PatientService patientService;

    @GetMapping
    public List<Patient> getPatients(){
        return patientService.getAll();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable("id") Long id) {
        return patientService.findByPatientId(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Patient create(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Patient update(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @PatchMapping(path="/{id}", consumes= "application/json")
    public Patient partialUpdate(@PathVariable("id") long id, @RequestBody Patient patchPatient) {
        Patient patient = PatientUtils.validatePatient(patchPatient, patientService.findByPatientId(id));

        return patientService.save(patient);
    }




    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        try {
            patientService.deleteById(id);
        }
        catch(EmptyResultDataAccessException e) {
            //No action
        }
    }

}

