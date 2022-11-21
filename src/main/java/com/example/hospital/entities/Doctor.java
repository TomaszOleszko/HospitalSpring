package com.example.hospital.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Doctor")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
public class Doctor extends PersonalData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorID;
    private String speciality;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor",fetch = FetchType.EAGER)
    List<Visit> visits;
}