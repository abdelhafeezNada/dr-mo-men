package org.abdelhafeez.dr_momen.repo;

import java.util.List;

import org.abdelhafeez.dr_momen.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByPatientId(Long id);

    Page<Appointment> findAllByPatientId(Long id, Pageable pageable);
}
