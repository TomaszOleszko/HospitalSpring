package com.example.hospital.services;
import com.example.hospital.dao.DoctorRepository;
import com.example.hospital.entities.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository docRepo;

    public Doctor save(Doctor doctor){
        return docRepo.save(doctor);
    }

    public List<Doctor> getAll(){
        return docRepo.findAll();
    }

    public Doctor findByDoctorId(Long Id){
        return docRepo.findById(Id).orElse(null);
    }

    public void delete(Doctor doctor){
        docRepo.delete(doctor);
    }
    public void deleteById(Long id){
        docRepo.deleteById(id);
    }
}
