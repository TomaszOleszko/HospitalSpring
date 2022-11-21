package com.example.hospital.entities;

import lombok.*;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
public class PersonalData implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String phone;
}
