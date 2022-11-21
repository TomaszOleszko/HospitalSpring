package com.example.hospital.Unit;


import com.example.hospital.entities.Visit;
import com.example.hospital.utils.VisitUtils;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class VisitUnitTest {

    @Test
    public void givenVisitType_whenValidateType_ReturnChangedVisitType() throws Exception {
        Visit patchVisit = new Visit();
        patchVisit.setTypeVisit("Type");
        Visit visit = new Visit();

        VisitUtils.validateVisit(patchVisit, visit);
        assertEquals(visit.getTypeVisit(), "Type");
    }

    @Test
    public void givenVisitType_whenInValidateType_ReturnUnChangedVisitType() throws Exception {
        Visit patchVisit = new Visit();
        patchVisit.setTypeVisit(null);
        Visit visit = new Visit();

        VisitUtils.validateVisit(patchVisit, visit);
        assertNull(visit.getTypeVisit());
    }

    @Test
    public void givenVisit_whenValidateType_ReturnChangedVisit() throws Exception {
        Visit patchVisit = new Visit();
        patchVisit.setTypeVisit("Type");
        patchVisit.setVisitDate(new Date(2000,12,12));
        Visit visit = new Visit();

        VisitUtils.validateVisit(patchVisit, visit);

        assertAll("Transaction quota",
                () ->  assertEquals(visit.getTypeVisit(),"Type"),
                () -> assertEquals(visit.getVisitDate(),new Date(2000,12,12))
        );
    }
}
