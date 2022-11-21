package com.example.hospital.services;

import com.example.hospital.dao.VisitRepository;
import com.example.hospital.entities.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitService {

    @Autowired
    VisitRepository visRepo;

    public Visit save(Visit visit){
        return visRepo.save(visit);
    }

    public List<Visit> getAll(){
        return visRepo.findAll();
    }

    public Visit findByVisitId(Long Id){
        return visRepo.findById(Id).orElse(null);
    }

    public void delete(Visit visit){
        visRepo.delete(visit);
    }

    public void deleteById(Long id){
        visRepo.deleteById(id);
    }
}

