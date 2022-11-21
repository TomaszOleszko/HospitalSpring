package com.example.hospital.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Patient")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Patient extends PersonalData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientID;
    private String pesel;
    private String city;
    private String country;
    private String postalCode;

    @JsonIgnore
    @OneToMany(mappedBy="patient", fetch = FetchType.EAGER)
    List<Visit> visits;

}


