package com.example.hospital.services;

import com.example.hospital.dao.PatientRepository;
import com.example.hospital.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepository patRepo;

    public Patient save(Patient patient){
        return patRepo.save(patient);
    }

    public List<Patient> getAll(){
        return patRepo.findAll();
    }

    public Patient findByPatientId(Long Id){
        return patRepo.findById(Id).orElse(null);
    }

    public void delete(Patient patient){
        patRepo.delete(patient);
    }

    public void deleteById(Long id){
        patRepo.deleteById(id);
    }
}

