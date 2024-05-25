package org.abdelhafeez.dr_momen.repo;

import org.abdelhafeez.dr_momen.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {

}
