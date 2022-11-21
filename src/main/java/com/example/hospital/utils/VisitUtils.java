package com.example.hospital.utils;

import com.example.hospital.entities.Visit;

public class VisitUtils {
    public static Visit validateVisit(Visit patchVisit, Visit visit) {
        if (patchVisit.getVisitDate() != null) {
            visit.setVisitDate(patchVisit.getVisitDate());
        }
        if (patchVisit.getTypeVisit() != null) {
            visit.setTypeVisit(patchVisit.getTypeVisit());
        }
        return visit;
    }
}
