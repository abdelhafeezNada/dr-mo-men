package org.abdelhafeez.dr_momen.service;

import org.abdelhafeez.dr_momen.entity.Appointment;
import org.abdelhafeez.dr_momen.repo.AppointmentRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;

    public Page<Appointment> findByPatientId(Long patientId, int page) {
        return appointmentRepo.findAllByPatientId(patientId, PageRequest.of(page, 10));
    }

    public Page<Appointment> findAll(int page) {
        return appointmentRepo.findAll(PageRequest.of(page, 10));
    }

    public Appointment findById(Long id) {
        return appointmentRepo.findById(id).orElse(null);
    }

    public void save(Appointment appointment) {
        appointmentRepo.save(appointment);
    }
}
