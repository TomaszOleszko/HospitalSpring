package com.example.hospital.controllers;

import com.example.hospital.entities.Visit;
import com.example.hospital.services.DoctorService;
import com.example.hospital.services.PatientService;
import com.example.hospital.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app-api/visits")
public class VisitApiController {
    @Autowired
    VisitService visitService;

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @GetMapping
    public List<Visit> getVisits() {
        return visitService.getAll();
    }

    @GetMapping("/{id}")
    public Visit getVisitById(@PathVariable("id") Long id) {
        return visitService.findByVisitId(id);
    }

    @PostMapping(path = "/{patient}/{doctor}", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Visit create(@RequestBody Visit visit, @PathVariable("patient") Long patient, @PathVariable("doctor") Long doctor) {
        visit.setDoctor(doctorService.findByDoctorId(doctor));
        visit.setPatient(patientService.findByPatientId(patient));
        return visitService.save(visit);
    }

    @PutMapping(path = "/{patient}/{doctor}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Visit update(@RequestBody Visit visit, @PathVariable("patient") Long patient, @PathVariable("doctor") Long doctor) {
        visit.setDoctor(doctorService.findByDoctorId(doctor));
        visit.setPatient(patientService.findByPatientId(patient));
        return visitService.save(visit);
    }

    @PatchMapping(path = "/{id}/{patient}/{doctor}", consumes = "application/json")
    public Visit partialUpdate(@PathVariable("id") long id, @RequestBody Visit patchVisit, @PathVariable("patient") Long patient, @PathVariable("doctor") Long doctor) {
        Visit visit = visitService.findByVisitId(id);
        visit.setDoctor(doctorService.findByDoctorId(doctor));
        visit.setPatient(patientService.findByPatientId(patient));

        if (patchVisit.getVisitDate() != null) {
            visit.setVisitDate(patchVisit.getVisitDate());
        }
        if (patchVisit.getTypeVisit() != null) {
            visit.setTypeVisit(patchVisit.getTypeVisit());
        }

        return visitService.save(visit);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        try {
            visitService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            //No action
        }
    }

}
