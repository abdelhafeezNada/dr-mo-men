package org.abdelhafeez.dr_momen.service;

import org.abdelhafeez.dr_momen.entity.Patient;
import org.abdelhafeez.dr_momen.repo.PatientRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepo patientRepo;

    public Page<Patient> findPage(int pageNum) {
        return patientRepo.findAll(PageRequest.of(pageNum, 10));
    }

    public void save(Patient patient) {
        patientRepo.save(patient);
    }

    public long count() {
        return patientRepo.count();
    }

}
