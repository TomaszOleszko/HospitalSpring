package com.example.hospital.dao;

import com.example.hospital.entities.Visit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long> {

    @Override
    List<Visit> findAll();
}
