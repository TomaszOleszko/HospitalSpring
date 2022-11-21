package com.example.hospital.controllers;

import com.example.hospital.entities.Doctor;
import com.example.hospital.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app-api/doctors")
public class DoctorApiController {
    @Autowired
    DoctorService doctorService;

    @GetMapping
    public List<Doctor> getDoctors() {
        return doctorService.getAll();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable("id") Long id) {
        return doctorService.findByDoctorId(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor create(@RequestBody Doctor doctor) {
        return doctorService.save(doctor);
    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Doctor update(@RequestBody Doctor doctor) {
        return doctorService.save(doctor);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    public Doctor partialUpdate(@PathVariable("id") long id, @RequestBody Doctor patchDoctor) {
        Doctor doctor = doctorService.findByDoctorId(id);

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

        return doctorService.save(doctor);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        try {
            doctorService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            //No action
        }
    }
}